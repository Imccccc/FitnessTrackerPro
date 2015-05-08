/*
SQLyog Ultimate v11.42 (64 bit)
MySQL - 5.5.43-0+deb7u1 : Database - fitness
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fitness` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `fitness`;

/*Table structure for table `account` */

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `username` varchar(150) NOT NULL DEFAULT '',
  `password` varchar(150) DEFAULT NULL,
  `Competeable` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `activity` */

DROP TABLE IF EXISTS `activity`;

CREATE TABLE `activity` (
  `activityid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL,
  `unitsecond` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`activityid`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `personalexerciseamount` */

DROP TABLE IF EXISTS `personalexerciseamount`;

CREATE TABLE `personalexerciseamount` (
  `username` varchar(150) NOT NULL,
  `date` date NOT NULL,
  `calories` decimal(10,2) NOT NULL,
  PRIMARY KEY (`username`,`date`),
  CONSTRAINT `personalexerciseamount_ibfk_1` FOREIGN KEY (`username`) REFERENCES `account` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `plan` */

DROP TABLE IF EXISTS `plan`;

CREATE TABLE `plan` (
  `planid` int(11) NOT NULL,
  `weekday` int(11) NOT NULL,
  `activityid` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  KEY `planid` (`planid`),
  KEY `activityid` (`activityid`),
  CONSTRAINT `plan_ibfk_1` FOREIGN KEY (`planid`) REFERENCES `sharedplan` (`planid`) ON DELETE CASCADE,
  CONSTRAINT `plan_ibfk_2` FOREIGN KEY (`activityid`) REFERENCES `activity` (`activityid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `rating` */

DROP TABLE IF EXISTS `rating`;

CREATE TABLE `rating` (
  `username` varchar(150) NOT NULL,
  `planid` int(11) NOT NULL,
  `rating` double NOT NULL DEFAULT '5',
  `comments` mediumtext,
  PRIMARY KEY (`username`,`planid`),
  KEY `planid` (`planid`),
  KEY `username` (`username`),
  CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`planid`) REFERENCES `plan` (`planid`) ON DELETE CASCADE,
  CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`username`) REFERENCES `account` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Table structure for table `sharedplan` */

DROP TABLE IF EXISTS `sharedplan`;

CREATE TABLE `sharedplan` (
  `planid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(150) DEFAULT NULL,
  `plantype` varchar(150) DEFAULT NULL,
  `planname` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`planid`),
  KEY `username` (`username`),
  CONSTRAINT `sharedplan_ibfk_1` FOREIGN KEY (`username`) REFERENCES `account` (`username`) ON DELETE CASCADE,
  CONSTRAINT `sharedplan_ibfk_2` FOREIGN KEY (`username`) REFERENCES `account` (`username`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
