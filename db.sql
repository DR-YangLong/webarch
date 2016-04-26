/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.6.21-enterprise-commercial-advanced : Database - shiro
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`shiro` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `shiro`;
DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `perm_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `perm_name` VARCHAR(10) NOT NULL COMMENT '编号名称',
  `perm_mark` VARCHAR(256) DEFAULT NULL COMMENT '权限描述',
  `perm_creater` VARCHAR(30) DEFAULT NULL COMMENT '创建人',
  `perm_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `perm_status` CHAR(1) NOT NULL DEFAULT '0' COMMENT '启用标记：0启用，1失效',
  PRIMARY KEY (`perm_id`),
  UNIQUE KEY `uq_sys_perm_name` (`perm_name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='系统权限表';

/*Data for the table `sys_permission` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `status` VARCHAR(1) DEFAULT '0' COMMENT '0启用1禁用2锁定',
  `roles` VARCHAR(64) DEFAULT NULL,
  `perms` VARCHAR(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_username` (`username`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

/*Table structure for table `sys_resources` */

DROP TABLE IF EXISTS `sys_resources`;

CREATE TABLE `sys_resources` (
  `res_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `res_url` VARCHAR(256) NOT NULL COMMENT 'url地址',
  `res_creater` VARCHAR(30) DEFAULT NULL COMMENT '创建人',
  `res_mark` VARCHAR(256) DEFAULT NULL COMMENT '描述',
  `res_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `res_status` CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标记，删除1，未删除0',
  PRIMARY KEY (`res_id`)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统资源表，存放系统的URL地址';


/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `role_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_name` VARCHAR(10) NOT NULL COMMENT '角色名称',
  `role_mark` VARCHAR(256) DEFAULT NULL COMMENT '角色描述：描述角色的权限',
  `role_creater` VARCHAR(30) DEFAULT NULL COMMENT '创建人',
  `role_createtime` DATETIME DEFAULT NULL COMMENT '创建时间',
  `role_status` CHAR(1) NOT NULL DEFAULT '1' COMMENT '启用标记，0启用，1失效',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `uq_sys_role_name` (`role_name`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

/*Table structure for table `sys_res_perm` */

DROP TABLE IF EXISTS `sys_res_perm`;

CREATE TABLE `sys_res_perm` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `res_id` INT(11) DEFAULT NULL COMMENT '资源编号',
  `perm_id` INT(11) DEFAULT NULL COMMENT '权限编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sys_res_perm` (`res_id`,`perm_id`),
  KEY `FK_Reference_4` (`perm_id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`res_id`) REFERENCES `sys_resources` (`res_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`perm_id`) REFERENCES `sys_permission` (`perm_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='系统资源权限表';

DROP TABLE IF EXISTS `sys_res_role`;

CREATE TABLE `sys_res_role` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `res_id` INT(11) DEFAULT NULL COMMENT '资源编号',
  `role_id` INT(11) DEFAULT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sys_res_role` (`res_id`,`role_id`),
  KEY `FK_Reference_2` (`role_id`),
  CONSTRAINT `FK_Reference_1` FOREIGN KEY (`res_id`) REFERENCES `sys_resources` (`res_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统资源角色分配表';


INSERT  INTO `sys_resources`(`res_id`,`res_url`,`res_creater`,`res_mark`,`res_time`,`res_status`) VALUES (1,'/login.jsp',NULL,'登陆页',NULL,'0'),(2,'/shiro/login',NULL,'登陆',NULL,'0');
INSERT  INTO `sys_role`(`role_id`,`role_name`,`role_mark`,`role_creater`,`role_createtime`,`role_status`) VALUES (1,'admin','系统管理员',NULL,NULL,'0'),(2,'roler1','第一个角色',NULL,NULL,'0'),(3,'roler2','第二个角色',NULL,NULL,'1');
INSERT  INTO `sys_res_role`(`id`,`res_id`,`role_id`) VALUES (1,1,3),(2,2,1),(3,2,2),(4,2,3);