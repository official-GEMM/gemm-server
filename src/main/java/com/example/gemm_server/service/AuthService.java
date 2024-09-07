package com.example.gemm_server.service;

import static com.example.gemm_server.common.code.error.MemberErrorCode.MEMBER_ALREADY_COMPLETED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.OWN_REFERRAL_CODE;
import static com.example.gemm_server.common.code.error.MemberErrorCode.PHONE_NUMBER_DUPLICATED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.PHONE_NUMBER_NOT_VALIDATED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.VERIFICATION_ATTEMPT_EXCEED;
import static com.example.gemm_server.common.code.error.MemberErrorCode.VERIFICATION_NOT_FOUND;
import static com.example.gemm_server.common.code.error.MemberErrorCode.VERIFICATION_NOT_MATCH;
import static com.example.gemm_server.common.constant.Policy.ATTENDANCE_COMPENSATION;
import static com.example.gemm_server.common.constant.Policy.REFERRAL_COMPENSATION;

import com.example.gemm_server.common.constant.TimeZone;
import com.example.gemm_server.common.enums.GemUsageType;
import com.example.gemm_server.common.util.DateUtil;
import com.example.gemm_server.common.util.RandomUtil;
import com.example.gemm_server.domain.entity.Member;
import com.example.gemm_server.domain.entity.redis.PhoneVerification;
import com.example.gemm_server.domain.entity.redis.TokenBlackList;
import com.example.gemm_server.domain.repository.redis.PhoneVerificationRepository;
import com.example.gemm_server.domain.repository.redis.RefreshTokenRepository;
import com.example.gemm_server.domain.repository.redis.TokenBlackListRepository;
import com.example.gemm_server.exception.MemberException;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

  @Value("${coolsms.api.key}")
  private String apiKey;
  @Value("${coolsms.api.secret}")
  private String apiSecretKey;
  @Value("${coolsms.api.fromnumber}")
  private String fromNumber;

  private final MemberService memberService;
  private final GemService gemService;
  private final TokenBlackListRepository tokenBlackListRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final NotificationService notificationService;
  private final PhoneVerificationRepository phoneVerificationRepository;

  private DefaultMessageService messageService;

  @PostConstruct
  private void init() {
    this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey,
        "https://api.coolsms.co.kr");
  }

  @Transactional
  public void logout(Long memberId, String token) {
    TokenBlackList blackList = new TokenBlackList(token);
    tokenBlackListRepository.save(blackList);

    refreshTokenRepository.deleteById(memberId);
  }

  @Transactional
  public boolean compensateMemberForDailyAttendance(Long memberId) {
    Member member = memberService.findMemberByMemberIdOrThrow(memberId);
    boolean isCompensated = false;
    if (!DateUtil.isToday(member.getLastLoginAt())) {
      gemService.saveChangesOfGemWithMember(member, ATTENDANCE_COMPENSATION,
          GemUsageType.COMPENSATION);
      isCompensated = true;
      // TODO: 출석 보상 알림 생성
    }
    member.setLastLoginAt(LocalDateTime.now(TimeZone.DEFAULT));
    return isCompensated;
  }

  @Transactional
  public void compensateMemberForReferralIfValid(Long referrerMemberId, String referralCode) {
    Member referrerMember = memberService.findMemberByMemberIdOrThrow(referrerMemberId);
    Member refereeMember = memberService.findMemberByReferralCodeOrThrow(referralCode);
    validateForReferral(referrerMember, refereeMember);

    gemService.saveChangesOfGemWithMember(referrerMember, REFERRAL_COMPENSATION,
        GemUsageType.COMPENSATION);
    gemService.saveChangesOfGemWithMember(refereeMember, REFERRAL_COMPENSATION,
        GemUsageType.COMPENSATION);

    notificationService.publishReferralNotification(refereeMember, referrerMember);
  }

  public void validatePhoneNumberForUpdate(Long memberId, String phoneNumber) {
    if (!isPhoneNumberValidated(phoneNumber)) {
      throw new MemberException(PHONE_NUMBER_NOT_VALIDATED);
    }
    if (memberService.isPhoneNumberDuplicated(memberId, phoneNumber)) {
      throw new MemberException(PHONE_NUMBER_DUPLICATED);
    }
  }

  protected boolean isPhoneNumberValidated(String phoneNumber) {
    Optional<PhoneVerification> phoneVerification = this.phoneVerificationRepository.findById(
        phoneNumber);
    return phoneVerification.isPresent() && phoneVerification.get().isVerified();
  }

  private void validateForReferral(Member referrerMember, Member refereeMember) {
    if (referrerMember.getIsRegistrationCompleted()) {
      throw new MemberException(MEMBER_ALREADY_COMPLETED);
    }

    long referrerMemberId = referrerMember.getId();
    long refereeMemberId = refereeMember.getId();
    if (referrerMemberId == refereeMemberId) {
      throw new MemberException(OWN_REFERRAL_CODE);
    }
  }

  public SingleMessageSentResponse sendVerificationCodeWithSms(String to,
      String verificationCode) {
    Message message = new Message();
    message.setFrom(fromNumber);
    message.setTo(to.replaceAll("-", "")); // 발신번호 및 수신번호 형식: 01012345678
    message.setText("[GEMM] 인증번호 [" + verificationCode + "]를 입력해주세요.");
    return this.messageService.sendOne(new SingleMessageSendingRequest(message));
  }

  public String generateAndSaveVerificationCode(String phoneNumber) {
    String verificationCode = RandomUtil.getRandomNumber(4);
    PhoneVerification phoneVerification = new PhoneVerification(phoneNumber,
        verificationCode);
    this.phoneVerificationRepository.save(phoneVerification);
    return verificationCode;
  }

  public PhoneVerification getPhoneVerificationWithIncrementingAttemptCount(String phoneNumber) {
    PhoneVerification phoneVerification = this.phoneVerificationRepository.findById(
        phoneNumber).orElseThrow(() -> new MemberException(VERIFICATION_NOT_FOUND));

    phoneVerification.incrementAttemptCount();
    return this.phoneVerificationRepository.save(phoneVerification);
  }

  public PhoneVerification validatePhoneVerification(PhoneVerification phoneVerification,
      String verificationCode) {
    if (!phoneVerification.isAttemptCountValid()) {
      throw new MemberException(VERIFICATION_ATTEMPT_EXCEED);
    }
    if (!phoneVerification.verify(verificationCode)) {
      throw new MemberException(VERIFICATION_NOT_MATCH);
    }
    phoneVerification.setVerified(true);
    return this.phoneVerificationRepository.save(phoneVerification);
  }
}
