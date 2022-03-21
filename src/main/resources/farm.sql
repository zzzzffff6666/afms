-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: farm
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alert`
--

DROP TABLE IF EXISTS `alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alert` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '告警名称',
  `type` int NOT NULL COMMENT '资源类型',
  `relate_id` int NOT NULL COMMENT '资源id',
  `user_id` int NOT NULL COMMENT '负责人',
  `content` varchar(10240) NOT NULL COMMENT '告警内容',
  `level` int NOT NULL COMMENT '告警等级',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `status` int NOT NULL COMMENT '告警状态',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `alert_name_index` (`name`),
  KEY `alert_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alert`
--

LOCK TABLES `alert` WRITE;
/*!40000 ALTER TABLE `alert` DISABLE KEYS */;
/*!40000 ALTER TABLE `alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '联系人',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `wechat` varchar(100) DEFAULT NULL COMMENT '微信',
  `addr` varchar(100) NOT NULL COMMENT '地址',
  `name_ep` varchar(100) NOT NULL COMMENT '企业名称',
  `addr_ep` varchar(100) NOT NULL COMMENT '企业地址',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_phone_uindex` (`phone`),
  KEY `client_name_ep_index` (`name_ep`),
  KEY `client_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '评价人',
  `type` int NOT NULL COMMENT '资源类型',
  `relate_id` int NOT NULL COMMENT '资源id',
  `score` int NOT NULL COMMENT '评分',
  `content` varchar(1000) NOT NULL COMMENT '评价内容',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `comment_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_task`
--

DROP TABLE IF EXISTS `daily_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `daily_task` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '负责人',
  `task_id` int NOT NULL COMMENT '任务id',
  `start_pre` datetime DEFAULT NULL COMMENT '预期开始时间',
  `end_pre` datetime DEFAULT NULL COMMENT '预期结束时间',
  `start_act` datetime DEFAULT NULL COMMENT '实际开始时间',
  `end_act` datetime DEFAULT NULL COMMENT '实际结束时间',
  `status` int NOT NULL COMMENT '任务状态',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `daily_task_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_task`
--

LOCK TABLES `daily_task` WRITE;
/*!40000 ALTER TABLE `daily_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `daily_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fund`
--

DROP TABLE IF EXISTS `fund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fund` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '收支名称',
  `type` int NOT NULL COMMENT '收支类型',
  `amount` decimal(12,2) NOT NULL COMMENT '金额',
  `content` varchar(10240) NOT NULL COMMENT '收支内容',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `fund_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fund`
--

LOCK TABLES `fund` WRITE;
/*!40000 ALTER TABLE `fund` DISABLE KEYS */;
/*!40000 ALTER TABLE `fund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int NOT NULL COMMENT '物资种类',
  `store_id` int NOT NULL COMMENT '仓库id',
  `name` varchar(100) NOT NULL COMMENT '物资名称',
  `amount` int NOT NULL COMMENT '物资数量',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `maintain_time` datetime DEFAULT NULL COMMENT '维护时间',
  `maintain_interval` int DEFAULT NULL COMMENT '维护间隔天数',
  `status` int NOT NULL COMMENT '物资状态',
  `url` varchar(200) NOT NULL COMMENT 'url地址',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `item_store_id_index` (`store_id`),
  KEY `item_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '执行人',
  `task_id` int NOT NULL COMMENT '任务ID',
  `type` int NOT NULL COMMENT '资源类型',
  `relate_id` int NOT NULL COMMENT '资源id',
  `deadline` datetime NOT NULL COMMENT '截止时间',
  `status` int NOT NULL COMMENT '任务状态',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `job_user_id_type_relate_id_uindex` (`user_id`,`type`,`relate_id`),
  KEY `job_deadline_index` (`deadline`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int NOT NULL COMMENT '资源类型',
  `relate_id` int NOT NULL COMMENT '资源id',
  `user_id` int NOT NULL COMMENT '员工id',
  `operation_id` int NOT NULL COMMENT '操作类型id',
  `operation` varchar(40) NOT NULL COMMENT '操作类型名称',
  `old_value` varchar(1000) DEFAULT NULL COMMENT '旧值',
  `new_value` varchar(1000) DEFAULT NULL COMMENT '新值',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `log_user_id_index` (`user_id`),
  KEY `log_add_time_index` (`add_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户id',
  `auth` int NOT NULL COMMENT '权限类型',
  `type` int DEFAULT NULL COMMENT '资源类型',
  `relate_id` int DEFAULT NULL COMMENT '资源id',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_user_id_type_uindex` (`user_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '计划名称',
  `use_num` int NOT NULL DEFAULT '0' COMMENT '使用次数',
  `task_list` varchar(10240) NOT NULL COMMENT '任务列表',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `plan_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pool`
--

DROP TABLE IF EXISTS `pool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pool` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `place` int NOT NULL COMMENT '车间号',
  `ordinal` int NOT NULL COMMENT '池序号',
  `length` decimal(5,2) NOT NULL COMMENT '长',
  `width` decimal(5,2) NOT NULL COMMENT '宽',
  `height` decimal(5,2) NOT NULL COMMENT '高',
  `type` int NOT NULL COMMENT '池类型',
  `usage` varchar(1000) NOT NULL COMMENT '用途',
  `status` int NOT NULL COMMENT '状态',
  `detail` varchar(10240) DEFAULT NULL COMMENT '扩展属性',
  `url` varchar(200) NOT NULL COMMENT 'url地址',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pool_place_ordinal_uindex` (`place`,`ordinal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pool`
--

LOCK TABLES `pool` WRITE;
/*!40000 ALTER TABLE `pool` DISABLE KEYS */;
/*!40000 ALTER TABLE `pool` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pool_cycle`
--

DROP TABLE IF EXISTS `pool_cycle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pool_cycle` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pool_id` int NOT NULL COMMENT '养殖池id',
  `cycle` int NOT NULL COMMENT '养殖周期号',
  `user_id` int NOT NULL COMMENT '负责人',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `status` int NOT NULL COMMENT '周期状态',
  `cost` decimal(8,2) DEFAULT NULL COMMENT '总花费',
  `income` decimal(8,2) DEFAULT NULL COMMENT '总收入',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pool_cycle_pool_id_cycle_uindex` (`pool_id`,`cycle`),
  KEY `pool_cycle_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pool_cycle`
--

LOCK TABLES `pool_cycle` WRITE;
/*!40000 ALTER TABLE `pool_cycle` DISABLE KEYS */;
/*!40000 ALTER TABLE `pool_cycle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pool_plan`
--

DROP TABLE IF EXISTS `pool_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pool_plan` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `plan_id` int NOT NULL COMMENT '计划id',
  `pool_id` int NOT NULL COMMENT '养殖池id',
  `cycle` int NOT NULL COMMENT '养殖周期号',
  `start_pre` datetime DEFAULT NULL COMMENT '预期开始时间',
  `end_pre` datetime DEFAULT NULL COMMENT '预期结束时间',
  `start_act` datetime DEFAULT NULL COMMENT '实际开始时间',
  `end_act` datetime DEFAULT NULL COMMENT '实际结束时间',
  `finish` int DEFAULT NULL COMMENT '完成情况',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pool_plan_pool_id_cycle_uindex` (`pool_id`,`cycle`),
  KEY `pool_plan_plan_id_index` (`plan_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pool_plan`
--

LOCK TABLES `pool_plan` WRITE;
/*!40000 ALTER TABLE `pool_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `pool_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pool_task`
--

DROP TABLE IF EXISTS `pool_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pool_task` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pool_id` int NOT NULL COMMENT '养殖池id',
  `cycle` int NOT NULL COMMENT '养殖周期号',
  `user_id` int NOT NULL COMMENT '负责人',
  `task_id` int NOT NULL COMMENT '任务id',
  `start_pre` datetime DEFAULT NULL COMMENT '预期开始时间',
  `end_pre` datetime DEFAULT NULL COMMENT '预期结束时间',
  `start_act` datetime DEFAULT NULL COMMENT '实际开始时间',
  `end_act` datetime DEFAULT NULL COMMENT '实际结束时间',
  `status` int NOT NULL COMMENT '任务状态',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `pool_task_pool_id_index` (`pool_id`),
  KEY `pool_task_user_id_index` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pool_task`
--

LOCK TABLES `pool_task` WRITE;
/*!40000 ALTER TABLE `pool_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `pool_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) NOT NULL COMMENT '仓库名称',
  `manager` int NOT NULL COMMENT '管理员',
  `url` varchar(200) NOT NULL COMMENT 'url地址',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `store_manager_index` (`manager`),
  KEY `store_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` int NOT NULL COMMENT '任务类型',
  `name` varchar(100) NOT NULL COMMENT '任务名称',
  `level` int NOT NULL COMMENT '优先等级',
  `content` varchar(10240) NOT NULL COMMENT '任务内容',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `task_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(20) NOT NULL COMMENT '随机盐值',
  `name` varchar(100) NOT NULL COMMENT '真实姓名',
  `card_id` varchar(20) NOT NULL COMMENT '身份证号',
  `status` int NOT NULL COMMENT '用户状态',
  `add_time` datetime NOT NULL COMMENT '创建时间',
  `add_user` int NOT NULL COMMENT '创建人',
  `mod_time` datetime DEFAULT NULL COMMENT '修改时间',
  `mod_user` int DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_phone_uindex` (`phone`),
  UNIQUE KEY `user_card_id_uindex` (`card_id`),
  KEY `user_name_index` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verify`
--

DROP TABLE IF EXISTS `verify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verify` (
  `phone` varchar(11) NOT NULL COMMENT '手机号',
  `code` varchar(6) NOT NULL COMMENT '验证码',
  `expire_time` datetime NOT NULL COMMENT '失效时间',
  `verify_time` datetime DEFAULT NULL COMMENT '验证时间',
  `status` int NOT NULL COMMENT '验证码状态',
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verify`
--

LOCK TABLES `verify` WRITE;
/*!40000 ALTER TABLE `verify` DISABLE KEYS */;
/*!40000 ALTER TABLE `verify` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-21 15:03:57
