SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE `countries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(3) NOT NULL,
  `region` varchar(255),
  `income_group` varchar(5000),
  `special_notes` varchar(5000),
  PRIMARY KEY (`id`),	
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `indicators` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `source_note` varchar(5000),
  `source_organization` varchar(5000),
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `years` (
  `country_id` int(11) NOT NULL,
  `indicator_id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `value` float NOT NULL,
  PRIMARY KEY (`country_id`,`indicator_id`,`year`),
  KEY `indicator_id_fk` (`indicator_id`),
  CONSTRAINT `country_id_fk` FOREIGN KEY (`country_id`) REFERENCES `countries` (`id`),
  CONSTRAINT `indicator_id_fk` FOREIGN KEY (`indicator_id`) REFERENCES `indicators` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;