CREATE TABLE `analytics`
(
    `id`                    bigint        NOT NULL AUTO_INCREMENT,
    `created_at`            datetime(3)          NOT NULL,
    `deleted_at`            datetime(3) DEFAULT NULL,
    `directory_path`        varchar(255)  NOT NULL,
    `file_name`             varchar(255)  NOT NULL,
    `origin_name`           varchar(255)  NOT NULL,
    `category`              enum (
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
    `nickname`              varchar(30) DEFAULT NULL,
    `layout_completeness`   decimal(5, 2) NOT NULL,
    `readability`           decimal(5, 2) NOT NULL,
    `generation_time`       smallint      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;