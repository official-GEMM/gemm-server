drop table if exists `thumbnail` cascade;
drop table if exists `scrap` cascade;
drop table if exists `review` cascade;
drop table if exists `profile_image` cascade;
drop table if exists `notification` cascade;
drop table if exists `material` cascade;
drop table if exists `market_item` cascade;
drop table if exists `generation` cascade;
drop table if exists `gem` cascade;
drop table if exists `deal` cascade;
drop table if exists `member` cascade;
drop table if exists `banner` cascade;
drop table if exists `activity` cascade;

CREATE TABLE `banner`
(
    `created_at`     datetime(3)          NOT NULL,
    `deleted_at`     datetime(3) DEFAULT NULL,
    `id`             bigint               NOT NULL AUTO_INCREMENT,
    `directory_path` varchar(255)         NOT NULL,
    `file_name`      varchar(255)         NOT NULL,
    `origin_name`    varchar(255)         NOT NULL,
    `location`       enum ('MAIN', 'SUB') NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `activity`
(
    `age`           smallint    NOT NULL,
    `material_type` bit(3)      NOT NULL,
    `created_at`    datetime(3) NOT NULL,
    `deleted_at`    datetime(3) DEFAULT NULL,
    `id`            bigint      NOT NULL AUTO_INCREMENT,
    `title`         varchar(90) NOT NULL,
    `content`       text        NOT NULL,
    `category`      enum (
        'ART_AREA',
        'ART_PLAY',
        'FAIRY_TALE_POEM_PLAY',
        'GAME_PHYSICAL_PLAY',
        'LANGUAGE_AREA',
        'NUMERIC_MANIPULATION_AREA',
        'OTHER',
        'ROLE_AREA',
        'SCIENCE_AREA',
        'SPECIAL_ACTIVITY_PLAY',
        'STORY_DISCUSSION_PLAY'
        )                       NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `member`
(
    `birth`                     date                                       DEFAULT NULL,
    `gem`                       int                               NOT NULL DEFAULT '0',
    `is_registration_completed` bit(1)                            NOT NULL DEFAULT b'0',
    `manage_age`                smallint                                   DEFAULT NULL,
    `banned_at`                 datetime(3)                                DEFAULT NULL,
    `created_at`                datetime(3)                       NOT NULL,
    `deleted_at`                datetime(3)                                DEFAULT NULL,
    `id`                        bigint                            NOT NULL AUTO_INCREMENT,
    `last_login_at`             datetime(3)                                DEFAULT NULL,
    `referral_code`             varchar(8)                        NOT NULL,
    `phone_number`              varchar(15)                                DEFAULT NULL,
    `nickname`                  varchar(30)                                DEFAULT NULL,
    `name`                      varchar(90)                       NOT NULL,
    `social_id`                 varchar(255)                      NOT NULL,
    `provider`                  enum ('GOOGLE', 'KAKAO', 'NAVER') NOT NULL,
    `role`                      enum ('ADMIN', 'USER')            NOT NULL DEFAULT 'USER',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_member__social_id_provider` (`social_id`, `provider`),
    UNIQUE KEY `UK_member__referral_code` (`referral_code`),
    UNIQUE KEY `UK_member__phone_number` (`phone_number`),
    UNIQUE KEY `UK_member__nickname` (`nickname`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `deal`
(
    `price`       int         NOT NULL DEFAULT '0',
    `activity_id` bigint      NOT NULL,
    `buyer_id`    bigint      NOT NULL,
    `created_at`  datetime(3) NOT NULL,
    `deleted_at`  datetime(3)          DEFAULT NULL,
    `id`          bigint      NOT NULL AUTO_INCREMENT,
    `seller_id`   bigint      NOT NULL,
    PRIMARY KEY (`id`),
    KEY `IDX_deal__activity_id` (`activity_id`),
    KEY `IDX_deal__buyer_id` (`buyer_id`),
    KEY `IDX_deal__seller_id` (`seller_id`),
    CONSTRAINT `FK_deal__seller_id` FOREIGN KEY (`seller_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FK_deal__activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
    CONSTRAINT `FK_deal__buyer_id` FOREIGN KEY (`buyer_id`) REFERENCES `member` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `gem`
(
    `amount`     int         NOT NULL DEFAULT '0',
    `created_at` datetime(3) NOT NULL,
    `deleted_at` datetime(3)          DEFAULT NULL,
    `id`         bigint      NOT NULL AUTO_INCREMENT,
    `member_id`  bigint      NOT NULL,
    `usage_type` enum (
        'AI_USE',
        'CHARGE',
        'COMPENSATION',
        'MARKET_PURCHASE',
        'MARKET_SALE'
        )                    NOT NULL,
    PRIMARY KEY (`id`),
    KEY `IDX_gem__member_id` (`member_id`),
    CONSTRAINT `FK_gem__member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `generation`
(
    `activity_id` bigint      NOT NULL,
    `created_at`  datetime(3) NOT NULL,
    `deleted_at`  datetime(3) DEFAULT NULL,
    `id`          bigint      NOT NULL AUTO_INCREMENT,
    `owner_id`    bigint      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_generation__activity_id` (`activity_id`),
    KEY `IDX_generation__owner_id` (`owner_id`),
    CONSTRAINT `FK_gem__activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`),
    CONSTRAINT `FK_gem__owner_id` FOREIGN KEY (`owner_id`) REFERENCES `member` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `market_item`
(
    `average_score` float       NOT NULL DEFAULT '0',
    `month`         smallint             DEFAULT NULL,
    `price`         int         NOT NULL DEFAULT '0',
    `review_count`  int         NOT NULL DEFAULT '0',
    `scrap_count`   int         NOT NULL DEFAULT '0',
    `year`          smallint             DEFAULT NULL,
    `activity_id`   bigint      NOT NULL,
    `banned_at`     datetime(3)          DEFAULT NULL,
    `confirmed_at`  datetime(3)          DEFAULT NULL,
    `created_at`    datetime(3) NOT NULL,
    `deleted_at`    datetime(3)          DEFAULT NULL,
    `id`            bigint      NOT NULL AUTO_INCREMENT,
    `owner_id`      bigint      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `IDX_market_item__activity_id` (`activity_id`),
    KEY `IDX_market_item__owner_id` (`owner_id`),
    CONSTRAINT `FK_market_item__owner_id` FOREIGN KEY (`owner_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FK_market_item__activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `material`
(
    `activity_id`    bigint       NOT NULL,
    `created_at`     datetime(3)  NOT NULL,
    `deleted_at`     datetime(3) DEFAULT NULL,
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `directory_path` varchar(255) NOT NULL,
    `file_name`      varchar(255) NOT NULL,
    `origin_name`    varchar(255) NOT NULL,
    `type`           enum (
        'ACTIVITY_SHEET',
        'CUTOUT',
        'PPT'
        )                         NOT NULL,
    PRIMARY KEY (`id`),
    KEY `IDX_material__activity_id` (`activity_id`),
    CONSTRAINT `FK_material__activity_id` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `notification`
(
    `is_opened`   bit(1)       NOT NULL DEFAULT b'0',
    `created_at`  datetime(3)  NOT NULL,
    `deleted_at`  datetime(3)           DEFAULT NULL,
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `receiver_id` bigint       NOT NULL,
    `sender_id`   bigint                DEFAULT NULL,
    `subject_id`  bigint                DEFAULT NULL,
    `message`     varchar(180) NOT NULL,
    `event_type`  enum (
        'ATTENDANCE',
        'PURCHASE',
        'REFERRAL',
        'REVIEW'
        )                      NOT NULL,
    PRIMARY KEY (`id`),
    KEY `IDX_notification__receiver_id` (`receiver_id`),
    KEY `IDX_notification__sender_id` (`sender_id`),
    CONSTRAINT `FK_notification__receiver_id` FOREIGN KEY (`receiver_id`) REFERENCES `member` (`id`),
    CONSTRAINT `FK_notification__sender_id` FOREIGN KEY (`sender_id`) REFERENCES `member` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `profile_image`
(
    `created_at`     datetime(3)  NOT NULL,
    `deleted_at`     datetime(3) DEFAULT NULL,
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `member_id`      bigint      DEFAULT NULL,
    `directory_path` varchar(255) NOT NULL,
    `file_name`      varchar(255) NOT NULL,
    `origin_name`    varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_profile_image__member_id` (`member_id`),
    CONSTRAINT `FK_profile_image__member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `review`
(
    `score`          float        NOT NULL,
    `banned_at`      datetime(3) DEFAULT NULL,
    `created_at`     datetime(3)  NOT NULL,
    `deleted_at`     datetime(3) DEFAULT NULL,
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `market_item_id` bigint       NOT NULL,
    `member_id`      bigint       NOT NULL,
    `content`        varchar(600) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `IDX_review__market_item_id` (`market_item_id`),
    KEY `IDX_review__member_id` (`member_id`),
    CONSTRAINT `FK_review__market_item_id` FOREIGN KEY (`market_item_id`) REFERENCES `market_item` (`id`),
    CONSTRAINT `FK_review__member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `scrap`
(
    `created_at`     datetime(3) NOT NULL,
    `deleted_at`     datetime(3) DEFAULT NULL,
    `id`             bigint      NOT NULL AUTO_INCREMENT,
    `market_item_id` bigint      NOT NULL,
    `member_id`      bigint      NOT NULL,
    PRIMARY KEY (`id`),
    KEY `IDX_scrap__market_item_id` (`market_item_id`),
    KEY `IDX_scrap__member_id` (`member_id`),
    CONSTRAINT `FK_scrap__market_item_id` FOREIGN KEY (`market_item_id`) REFERENCES `market_item` (`id`),
    CONSTRAINT `FK_scrap__member_id` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `thumbnail`
(
    `sequence`       smallint     NOT NULL DEFAULT '0',
    `created_at`     datetime(3)  NOT NULL,
    `deleted_at`     datetime(3)           DEFAULT NULL,
    `id`             bigint       NOT NULL AUTO_INCREMENT,
    `material_id`    bigint       NOT NULL,
    `directory_path` varchar(255) NOT NULL,
    `file_name`      varchar(255) NOT NULL,
    `origin_name`    varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_thumbnail__material_id_sequence` (`material_id`, `sequence`),
    CONSTRAINT `FK_thumbnail__material_id` FOREIGN KEY (`material_id`) REFERENCES `material` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

