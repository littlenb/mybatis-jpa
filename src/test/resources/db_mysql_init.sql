-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.11-log

--
-- Table structure for table `t_relation_user_role`
--
CREATE TABLE `t_relation_user_role` (
  `id` bigint(18) NOT NULL,
  `user_id` bigint(18) DEFAULT NULL,
  `role_id` bigint(18) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `t_relation_user_role` VALUES (118299928123654321,118299928123543552,126364026828492800),(118299928123654322,118299928123543552,126364026828492801);

--
-- Table structure for table `t_sys_user`
--

CREATE TABLE `t_sys_user` (
  `id` bigint(18) NOT NULL COMMENT '主键',
  `uni_code` varchar(45) DEFAULT NULL COMMENT '用户识别码,唯一',
  `password_encrypt` varchar(255) DEFAULT NULL COMMENT '密码',
  `is_granted` tinyint(1) unsigned DEFAULT '0' COMMENT '是否授权',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

--
-- Dumping data for table `t_sys_user`
--

INSERT INTO `t_sys_user` VALUES (118299928123543552,'user_2','encrypt_text',0,'2017-12-27 19:00:00','2017-12-27 19:00:00'),(118299928123543553,'user_3','encrypt_text',1,'2017-12-27 19:00:00','2017-12-27 19:00:00');

--
-- Table structure for table `t_sys_user_archive`
--

CREATE TABLE `t_sys_user_archive` (
  `id` bigint(18) NOT NULL COMMENT '主键',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(1) unsigned DEFAULT NULL COMMENT '性别0-Male,1-FEMALE',
  `race` varchar(255) DEFAULT NULL COMMENT '民族',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `mobile_phone` varchar(45) DEFAULT NULL COMMENT '手机号码',
  `office_phone` varchar(45) DEFAULT NULL COMMENT '办公电话',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

--
-- Dumping data for table `t_sys_user_archive`
--

INSERT INTO `t_sys_user_archive` VALUES (118299928123543552,'user2','user2',0,'汉族','user2@qq.com','18200008888',NULL,'2017-12-27 19:00:00','2017-12-27 19:00:00'),(118299928123543553,'user3','user3',1,'满族','user3@qq.com','18300008888',NULL,'2017-12-27 19:00:00','2017-12-27 19:00:00');
