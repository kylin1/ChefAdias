DROP TABLE IF EXISTS `food_type`;
create table `food_type`(
	`id` int(11) not null auto_increment,
	`name` varchar(255) not null,
	`picture` varchar(255) not null,
	primary key(`id`)
) engine = InnoDB auto_increment=1 default charset=utf8;



DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `picture` varchar(255) NOT NULL,
  `price` decimal(10,5) NOT NULL,
  `like` int(11) NOT NULL DEFAULT '0',
  `dislike` int(11) NOT NULL DEFAULT '0',
  `type_id` int(11) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),

  CONSTRAINT `food_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `food_type` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


drop table if exists `user`;
create table `user`(
	`id` int(11) not null auto_increment,
	`username` varchar(255),
	`password` varchar(255) not null,
	`email` varchar(255) not null,
	`avatar` varchar(255),
	`address` varchar(255),
	`phone` varchar(255),  
	primary key(`id`)
) engine = InnoDB auto_increment=1 default charset=utf8;

drop table if exists `order`;
create table `order`(
	`id` int(11) not null auto_increment,
	`create_time` datetime, 
	`user_id` int(11) not null,
	`price` decimal(10,5) NOT NULL,
	`info` varchar(255),
	primary key(`id`),

	CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_id`) 
	REFERENCES `user` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB auto_increment=1 default charset=utf8;

drop table if exists `food_list`;
create table `food_list`(
	`id` int(11) not null auto_increment,
	`id` int(11) not null,
	`food_id` int(11) not null,
	`food_num` int(11) not null default '1',
	primary key(`id`),

CONSTRAINT `food_list_ibfk_1` FOREIGN KEY (`food_id`)
	REFERENCES `food` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE,

CONSTRAINT `food_list_ibfk_2` FOREIGN KEY (`id`)
	REFERENCES `order` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB auto_increment=1 default charset=utf8;

drop table if exists `easy_order`;
create table `easy_order`(
	`id` int(11) not null auto_increment,
	`id` int(11) not null,
	`user_id` int(11) not null,
	primary key(`id`),

	CONSTRAINT `easy_order_ibfk_1` FOREIGN KEY (`user_id`) 
	REFERENCES `user` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE,

	CONSTRAINT `easy_order_ibfk_2` FOREIGN KEY (`id`)
	REFERENCES `order` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB auto_increment=1 default charset=utf8;


drop table if exists `bowl`;
create table `bowl`(
	`id` int(11) not null auto_increment,
	`user_id` int(11),
	`is_return` tinyint(2) not null default '1',
	primary key(`id`),

	CONSTRAINT `bowl_ibfk_1` FOREIGN KEY (`user_id`) 
	REFERENCES `user` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB auto_increment=1 default charset=utf8;


drop table if exists `ticket`;
create table `ticket`(
	`id` int(11) not null auto_increment,
	`name` varchar(255) not null,
	`description` varchar(255) not null,
	`expire_day` int(11),
	`daily_upper` decimal(10,5) NOT NULL,
	primary key(`id`)
) engine = InnoDB auto_increment=1 default charset=utf8;

drop table if exists `user_ticket`;
create table `user_ticket`(
	`id` int(11) not null auto_increment,
	`user_id` int(11) not null,
	`ticket_id` int(11) not null,
	`bought_time` datetime not null, 
	primary key(`id`),

	CONSTRAINT `user_ticket_ibfk_1` FOREIGN KEY (`user_id`) 
	REFERENCES `user` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE,

CONSTRAINT `user_ticket_ibfk_2` FOREIGN KEY (`ticket_id`) 
	REFERENCES `ticket` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB auto_increment=1 default charset=utf8;

drop table if exists `food_extra`;
create table `food_extra`(
	`id` int(11) not null auto_increment,
	`food_id` int(11) not null,
	`extra_food_id` int(11) not null,
	primary key(`id`),

CONSTRAINT `food_extra_ibfk_1` FOREIGN KEY (`food_id`)
	REFERENCES `food` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE,

CONSTRAINT `food_extra_ibfk_2` FOREIGN KEY (`extra_food_id`) 
	REFERENCES `food` (`id`) 
	ON DELETE CASCADE ON UPDATE CASCADE
) engine = InnoDB auto_increment=1 default charset=utf8;














