package com.example.gemm_server.common.enums;

import lombok.Getter;

@Getter
public enum Category {
  ROLE_AREA("역할 영역"),
  LANGUAGE_AREA("언어 영역"),
  NUMERIC_MANIPULATION_AREA("수/조작 영역"),
  ART_AREA("미술 영역"),
  SCIENCE_AREA("과학 영역"),
  STORY_DISCUSSION_PLAY("이야기 나누기 놀이"),
  FAIRY_TALE_POEM_PLAY("동화/동시/동극 놀이"),
  ART_PLAY("미술 놀이"),
  GAME_PHYSICAL_PLAY("게임/신체 놀이"),
  SPECIAL_ACTIVITY_PLAY("특색활동 놀이"),
  OTHER("기타");

  private final String description;

  Category(String description) {
    this.description = description;
  }
}