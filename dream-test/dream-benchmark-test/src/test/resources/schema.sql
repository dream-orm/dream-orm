CREATE TABLE IF NOT EXISTS `account`
(
    `id`
    INTEGER
    PRIMARY
    KEY
    auto_increment,
    `user_name`
    VARCHAR
(
    100
),
    `password` VARCHAR
(
    128
),
    `salt` VARCHAR
(
    32
),
    `nickname` VARCHAR
(
    128
),
    `email` VARCHAR
(
    64
),
    `mobile` VARCHAR
(
    32
),
    `avatar` VARCHAR
(
    256
),
    `type` Integer,
    `status` Integer,
    `created` DATETIME,
    `options` VARCHAR
(
    2048
)
    );
