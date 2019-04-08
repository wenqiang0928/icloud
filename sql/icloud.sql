/*
SQLyog Ultimate v10.42 
MySQL - 5.7.20-log : Database - icloud
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`icloud` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `icloud`;

/*Table structure for table `docs` */

DROP TABLE IF EXISTS `docs`;

CREATE TABLE `docs` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '文件或者目录名称',
  `type` int(10) DEFAULT NULL COMMENT '1 目录 2  文件',
  `size` int(10) NOT NULL DEFAULT '0' COMMENT '文件大小 目录为0',
  `suffix` varchar(255) DEFAULT NULL COMMENT '文件类型 格式后缀',
  `md5_check_sum` varchar(255) DEFAULT NULL COMMENT '文件的md5校验值',
  `path` varchar(1000) NOT NULL COMMENT '目录的相对路径',
  `pid` int(10) DEFAULT NULL COMMENT '父亲id',
  `create_user_id` int(10) NOT NULL COMMENT '上传者userId',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_delete` int(10) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除 0-》1是',
  `case_no` varchar(255) DEFAULT NULL COMMENT '案件号',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `docs` */

insert  into `docs`(`id`,`name`,`type`,`size`,`suffix`,`md5_check_sum`,`path`,`pid`,`create_user_id`,`create_time`,`is_delete`,`case_no`,`modify_time`) values (1,'admin',1,0,NULL,NULL,'/admin',0,1,'2019-04-06 03:10:00',0,NULL,NULL),(2,'23456',1,0,NULL,NULL,'/admin/1',15,1,'2019-04-06 03:10:00',0,NULL,'2019-04-07 15:14:18'),(3,'2',1,0,NULL,NULL,'/admin/2',1,1,'2019-04-06 03:10:03',0,NULL,'2019-04-08 13:23:07'),(4,'3',1,0,NULL,NULL,'/admin/3',23,1,'2019-04-06 03:10:05',0,NULL,'2019-04-07 16:34:29'),(5,'1',1,0,NULL,NULL,'/admin/1/1',2,1,'2019-04-06 03:10:20',0,NULL,NULL),(6,'2',1,0,NULL,NULL,'/admin/1/2',2,1,'2019-04-06 03:10:23',0,NULL,NULL),(7,'3',1,0,NULL,NULL,'/admin/1/3',2,1,'2019-04-06 03:10:25',0,NULL,NULL),(8,'3',1,0,NULL,NULL,'/admin/1/1/3',5,1,'2019-04-06 03:10:35',0,NULL,NULL),(9,'a',1,0,NULL,NULL,'/admin/1/1/a',5,1,'2019-04-06 03:10:38',0,NULL,NULL),(10,'1',1,0,NULL,NULL,'/admin/1/1/1',5,1,'2019-04-06 03:10:41',0,NULL,NULL),(11,'2',1,0,NULL,NULL,'/admin/2/2',3,1,'2019-04-06 10:59:09',0,NULL,'2019-04-06 10:59:09'),(12,'2',1,0,NULL,NULL,'/admin/2/2',3,1,'2019-04-06 10:59:24',0,NULL,'2019-04-06 10:59:24'),(13,'2',1,0,NULL,NULL,'/admin/2/2',3,1,'2019-04-06 10:59:26',0,NULL,'2019-04-06 10:59:26'),(14,'2',1,0,NULL,NULL,'/admin/2/2',3,1,'2019-04-06 11:02:29',0,NULL,'2019-04-06 11:02:29'),(15,'1',1,0,NULL,NULL,'/admin/1',23,1,'2019-04-06 12:10:39',0,NULL,'2019-04-07 16:34:29'),(16,'adfsaf',1,0,NULL,NULL,'/admin/adfsaf',1,1,'2019-04-06 15:35:52',0,NULL,'2019-04-06 15:35:52'),(17,'baobao',1,0,NULL,NULL,'/admin/1/1/3/baobao',8,1,'2019-04-06 15:38:44',0,NULL,'2019-04-06 15:38:44'),(18,'text.txt',2,4,'.txt',NULL,'/adfsafasdfa',23,1,'2019-04-08 00:05:09',0,NULL,'2019-04-08 13:22:37'),(19,'text.mo',2,4,'.mo',NULL,'/adfsafasdfa',25,1,'2019-04-08 00:05:09',0,NULL,'2019-04-08 08:16:41'),(20,'text.png',2,4,'.png',NULL,'/adfsafasdfa',1,1,'2019-04-08 00:05:09',0,NULL,'2019-04-08 00:05:12'),(21,'text.avi',2,4,'.avi',NULL,'/adfsafasdfa',4,1,'2019-04-08 00:05:09',0,NULL,'2019-04-08 13:00:13'),(22,'text.mp3',2,4,'.mp3',NULL,'/adfsafasdfa',4,1,'2019-04-08 00:05:09',0,NULL,'2019-04-08 13:00:13'),(23,'123',1,0,NULL,NULL,'/',1,1,'2019-04-07 16:33:44',0,NULL,'2019-04-08 13:12:48'),(24,'二位若群翁',1,0,NULL,NULL,'/',23,1,'2019-04-08 00:48:19',0,NULL,'2019-04-08 00:48:28'),(25,'54320',1,0,NULL,NULL,'/',4,1,'2019-04-08 08:16:10',0,NULL,'2019-04-08 08:16:19');

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT '0',
  `parent_role_id` varchar(50) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色表';

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`parent_role_id`) values (1,'admin','0'),(2,'public','0');

/*Table structure for table `role_rule` */

DROP TABLE IF EXISTS `role_rule`;

CREATE TABLE `role_rule` (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `rule_id` int(11) NOT NULL COMMENT '权限id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色权限表';

/*Data for the table `role_rule` */

insert  into `role_rule`(`role_id`,`rule_id`) values (1,1);

/*Table structure for table `rule` */

DROP TABLE IF EXISTS `rule`;

CREATE TABLE `rule` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限表';

/*Data for the table `rule` */

insert  into `rule`(`id`,`name`) values (1,'all'),(2,'private');

/*Table structure for table `suffix_manage` */

DROP TABLE IF EXISTS `suffix_manage`;

CREATE TABLE `suffix_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL COMMENT '扩展名',
  `type` int(11) DEFAULT NULL COMMENT '类型 1图片 2文档 3视频 4音频 0其他',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_unique` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Data for the table `suffix_manage` */

insert  into `suffix_manage`(`id`,`name`,`type`) values (1,'.bmp',1),(2,'.jpg',1),(3,'.jpeg',1),(4,'.png',1),(5,'.gif',1),(6,'.doc',2),(7,'.docs',2),(8,'.xls',2),(9,'.xlsx',2),(10,'.ppt',2),(11,'.pptx',2),(12,'.txt',2),(13,'.avi',3),(14,'.mpg',3),(15,'.mlv',3),(16,'.mpe',3),(17,'.mov',3),(18,'.qt',3),(19,'.asf',3),(20,'.rm',3),(21,'.mp4',3),(22,'.rmvb',3),(23,'.mp3',4),(24,'.wav',4),(25,'.wma',4),(26,'.mid',4),(27,'.m4a',4);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(255) NOT NULL COMMENT '用户姓名',
  `alarm` varchar(255) NOT NULL COMMENT '警号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`alarm`,`password`,`role_id`) values (1,'admin','admin','admin',0),(4,'wxq','wxq','wxq',0);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色表';

/*Data for the table `user_role` */

insert  into `user_role`(`user_id`,`role_id`) values (1,1),(2,2),(2,2),(4,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
