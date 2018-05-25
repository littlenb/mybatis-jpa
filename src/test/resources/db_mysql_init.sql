-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_relation_user_role`
--

DROP TABLE IF EXISTS `t_relation_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_relation_user_role` (
  `relation_id` bigint(18) NOT NULL,
  `user_id` bigint(18) DEFAULT NULL,
  `role_id` bigint(18) DEFAULT NULL,
  PRIMARY KEY (`relation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_relation_user_role`
--

LOCK TABLES `t_relation_user_role` WRITE;
/*!40000 ALTER TABLE `t_relation_user_role` DISABLE KEYS */;
INSERT INTO `t_relation_user_role` VALUES (118299928123654321,118299928123543552,126364026828492800),(118299928123654322,118299928123543552,126364026828492801);
/*!40000 ALTER TABLE `t_relation_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user`
--

DROP TABLE IF EXISTS `t_sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_user` (
  `user_id` bigint(18) NOT NULL COMMENT '主键',
  `uni_code` varchar(45) DEFAULT NULL COMMENT '用户识别码,唯一',
  `password_encrypt` varchar(255) DEFAULT NULL COMMENT '密码',
  `is_granted` tinyint(1) unsigned DEFAULT '0' COMMENT '是否授权',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user`
--

LOCK TABLES `t_sys_user` WRITE;
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` VALUES (118299928123543552,'user_2','encrypt_text',0,'2017-12-27 19:00:00','2017-12-27 19:00:00'),(118299928123543553,'user_3','encrypt_text',1,'2017-12-27 19:00:00','2017-12-27 19:00:00');
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_sys_user_archive`
--

DROP TABLE IF EXISTS `t_sys_user_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_sys_user_archive` (
  `user_id` bigint(18) NOT NULL COMMENT '主键',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(1) unsigned DEFAULT NULL COMMENT '性别0-Male,1-FEMALE',
  `race` varchar(255) DEFAULT NULL COMMENT '民族',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `mobile_phone` varchar(45) DEFAULT NULL COMMENT '手机号码',
  `office_phone` varchar(45) DEFAULT NULL COMMENT '办公电话',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_sys_user_archive`
--

LOCK TABLES `t_sys_user_archive` WRITE;
/*!40000 ALTER TABLE `t_sys_user_archive` DISABLE KEYS */;
INSERT INTO `t_sys_user_archive` VALUES (118299928123543552,'user2','user2',0,'汉族','user2@qq.com','18200008888',NULL,'2017-12-27 19:00:00','2017-12-27 19:00:00'),(118299928123543553,'user3','user3',1,'满族','user3@qq.com','18300008888',NULL,'2017-12-27 19:00:00','2017-12-27 19:00:00');
/*!40000 ALTER TABLE `t_sys_user_archive` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-02 18:09:49
