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
  `is_delete` int(10) NOT NULL COMMENT '是否逻辑删除 0-》1是',
  `case_no` varchar(255) DEFAULT NULL COMMENT '案件号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `docs` */

insert  into `docs`(`id`,`name`,`type`,`size`,`suffix`,`md5_check_sum`,`path`,`pid`,`create_user_id`,`create_time`,`is_delete`,`case_no`) values (1,'admin',1,0,NULL,NULL,'/admin',0,1,'2019-04-05 15:20:23',0,NULL),(2,'dafsadfa',1,0,NULL,NULL,'/admin/dafsadfa',1,1,'2019-04-05 15:20:25',0,NULL);

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

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(255) NOT NULL COMMENT '用户姓名',
  `alarm` varchar(255) NOT NULL COMMENT '警号',
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`alarm`,`password`) values (1,'admin','admin','admin'),(4,'wxq','wxq','wxq');

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
