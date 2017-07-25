CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `user_name` varchar(45) DEFAULT NULL,
  `login_name` varchar(45) DEFAULT NULL,
  `password_alias` varchar(45) DEFAULT NULL,
  `mobile_phone` varchar(45) DEFAULT NULL,
  `office_phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `job` varchar(45) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `CREATE_TIME` timestamp NULL DEFAULT NULL,
  `UPDATE_TIME` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;