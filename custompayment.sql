CREATE DATABASE  IF NOT EXISTS `custompayment` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `custompayment`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: custompayment
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `check_order`
--

DROP TABLE IF EXISTS `check_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_order` (
  `id_order` int(11) NOT NULL AUTO_INCREMENT,
  `id_client` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `min_pay` int(11) NOT NULL,
  `status_check` int(11) NOT NULL,
  `status_pay` int(11) NOT NULL,
  `full_pay` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY (`id_order`),
  KEY `min_pay` (`min_pay`),
  KEY `id_client` (`id_client`),
  CONSTRAINT `check_order_ibfk_1` FOREIGN KEY (`id_client`) REFERENCES `clients` (`id_cli`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check_order`
--

LOCK TABLES `check_order` WRITE;
/*!40000 ALTER TABLE `check_order` DISABLE KEYS */;
INSERT INTO `check_order` VALUES (24,33,'2021-12-01','00:41:59',3000,0,4500,4500,131), (25,33,'2021-12-01','00:41:59',3000,28,4500,4500,131), (26,34,'2021-12-01','00:41:59',1500,1,45000,45000,132), (27,34,'2021-12-01','00:41:59',15000,1,20000,45000,132);
/*!40000 ALTER TABLE `check_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id_cli` int(11) NOT NULL AUTO_INCREMENT,
  `surname` text NOT NULL,
  `name` text NOT NULL,
  `num_passport` text NOT NULL,
  `pays_for_period` int(11) NOT NULL,
  PRIMARY KEY (`id_cli`),
  KEY `id_cli` (`id_cli`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (1,'Нейромонах','Феофан','123',0),(33,'Джеймс','Бонд','321',0),(34,'Deadpool','Deadpoolович','88',570);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cond_v`
--

DROP TABLE IF EXISTS `cond_v`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cond_v` (
  `conditional_v` int(11) NOT NULL,
  KEY `conditional_v` (`conditional_v`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cond_v`
--

LOCK TABLES `cond_v` WRITE;
/*!40000 ALTER TABLE `cond_v` DISABLE KEYS */;
INSERT INTO `cond_v` VALUES (3000);
/*!40000 ALTER TABLE `cond_v` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employees` (
  `id_emp` int(11) NOT NULL AUTO_INCREMENT,
  `surname` text NOT NULL,
  `name` text NOT NULL,
  `check_per_period` int(11) NOT NULL,
  `need_vacation` int(11) NOT NULL,
  `date_of_emp` date NOT NULL,
  `num_passport` text NOT NULL,
  PRIMARY KEY (`id_emp`),
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`id_emp`) REFERENCES `users` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'Воспяков','Илья',0,0,'2001-08-21','123456789'), (131, 'Петров', 'Александр',0,1,'1979-07-22', '987654321'),(132, 'Краснов', 'Олег',0,1,'1979-09-20', '987654');
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id_order` int(11) NOT NULL,
  `id_item` int(11) NOT NULL,
  `type` varchar(1) NOT NULL,
  `count` int(11) NOT NULL,
  UNIQUE KEY `id_order` (`id_order`,`id_item`),
  KEY `type` (`type`),
  KEY `id_item` (`id_item`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`id_order`) REFERENCES `check_order` (`id_order`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`type`) REFERENCES `type_item` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (24,1,'e',2),(25,2,'n',1),(26,1,'i',1),(27,2,'c',2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_item`
--

DROP TABLE IF EXISTS `type_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_item` (
  `type` varchar(1) NOT NULL,
  `perc_atop` int(11) NOT NULL,
  PRIMARY KEY (`type`),
  UNIQUE KEY `type` (`type`),
  KEY `type_2` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_item`
--

LOCK TABLES `type_item` WRITE;
/*!40000 ALTER TABLE `type_item` DISABLE KEYS */;
INSERT INTO `type_item` VALUES ('c',16),('e',17),('i',26),('n',14);
/*!40000 ALTER TABLE `type_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `login` text NOT NULL,
  `password` text NOT NULL,
  `role` int(11) NOT NULL,
  `online` int(11) DEFAULT '0',
  `ban` int(11) default '0',
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin',1,0,0),(131,'worker','worker',0,0,0), (132,'worker2','worker2',0,0,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'custompayment'
--

--
-- Dumping routines for database 'custompayment'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-08 22:32:26
