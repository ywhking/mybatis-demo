DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS `user`(
   `id` INT UNSIGNED AUTO_INCREMENT,
   `name` VARCHAR(100) NOT NULL,
   `password` VARCHAR(100) NOT NULL,
   `email` VARCHAR(40) NOT NULL,
   `phone_number` VARCHAR(40) NOT NULL,
   `state` SMALLINT DEFAULT 0,
   `create_date` timestamp DEFAULT current_timestamp,
   PRIMARY KEY ( `id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;