/*
 Navicat Premium Data Transfer

 Source Server         : iflytekQ
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : icloud

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 08/04/2019 18:00:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for docs
-- ----------------------------
DROP TABLE IF EXISTS `docs`;
CREATE TABLE `docs`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件或者目录名称',
  `type` int(10) NULL DEFAULT NULL COMMENT '1 目录 2  文件',
  `size` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '文件大小 目录为0',
  `suffix` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型 格式后缀',
  `md5_check_sum` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件的md5校验值',
  `path` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目录的相对路径',
  `pid` int(10) NULL DEFAULT NULL COMMENT '父亲id',
  `create_user_id` int(10) NOT NULL COMMENT '上传者userId',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `is_delete` int(10) NOT NULL DEFAULT 0 COMMENT '是否逻辑删除 0-》1是',
  `case_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '案件号',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `case_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '案件详情',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of docs
-- ----------------------------
INSERT INTO `docs` VALUES (1, 'admin', 1, '0', NULL, NULL, '/admin', 0, 1, '2019-04-06 03:10:00', 0, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (2, '1', 1, '0', NULL, NULL, '/admin/1', 1, 1, '2019-04-06 03:10:00', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (3, '2', 1, '0', NULL, NULL, '/admin/2', 1, 1, '2019-04-06 03:10:03', 0, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (4, '3', 1, '0', NULL, NULL, '/admin/3', 1, 1, '2019-04-06 03:10:05', 0, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (5, '1', 1, '0', NULL, NULL, '/admin/1/1', 2, 1, '2019-04-06 03:10:20', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (6, '2', 1, '0', NULL, NULL, '/admin/1/2', 2, 1, '2019-04-06 03:10:23', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (7, '3', 1, '0', NULL, NULL, '/admin/1/3', 2, 1, '2019-04-06 03:10:25', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (8, '3', 1, '0', NULL, NULL, '/admin/1/1/3', 5, 1, '2019-04-06 03:10:35', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (9, 'a', 1, '0', NULL, NULL, '/admin/1/1/a', 5, 1, '2019-04-06 03:10:38', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (41, '对对对', 1, '0', NULL, NULL, '/admin/3/对对对', 4, 1, '2019-04-08 07:52:45', 0, NULL, '2019-04-08 07:52:45', NULL);
INSERT INTO `docs` VALUES (42, 'log-info-2018-12-19.0.log', 2, '211638', 'log', 'cc40e8f733609491cdf81f51710fb0df', '/admin/3/对对对/log-info-2018-12-19.0.log', 41, 1, '2019-04-08 07:52:59', 0, NULL, '2019-04-08 07:52:59', NULL);
INSERT INTO `docs` VALUES (43, 'log-info-2018-12-05.0.log', 2, '17.6 KB', 'log', '32f99a6d4931c194a84e4c1c12f4c54b', '/admin/3/对对对/log-info-2018-12-05.0.log', 41, 1, '2019-04-08 08:02:16', 0, NULL, '2019-04-08 08:02:16', NULL);
INSERT INTO `docs` VALUES (44, 'log-info-2018-12-04.0.log', 2, '107 KB', 'log', '05f9103a0143bed051aaea60cf7af896', '/admin/3/对对对/log-info-2018-12-04.0.log', 41, 1, '2019-04-08 08:03:43', 0, '333', '2019-04-08 08:03:43', '4454');
INSERT INTO `docs` VALUES (45, 'test', 1, '0', NULL, NULL, '/test', 0, 5, '2019-04-08 08:36:05', 0, NULL, '2019-04-08 08:36:05', NULL);
INSERT INTO `docs` VALUES (46, 'test', 1, '0', NULL, NULL, '/test/test', 45, 5, '2019-04-08 08:36:11', 0, NULL, '2019-04-08 08:36:11', NULL);
INSERT INTO `docs` VALUES (47, 'log-warn-2018-12-19.0.log', 2, '127 B', 'log', '072fbff73780aa54542c1659639bf8b7', '/test/test/log-warn-2018-12-19.0.log', 46, 5, '2019-04-08 08:51:17', 0, NULL, '2019-04-08 08:51:17', NULL);

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名称',
  `md5` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径',
  `upload_time` datetime(0) NOT NULL COMMENT '上传时间',
  `upload_user_id` int(11) NOT NULL COMMENT '上传者id',
  `case_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '案件号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `parent_role_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '0');
INSERT INTO `role` VALUES (2, 'public', '0');

-- ----------------------------
-- Table structure for role_rule
-- ----------------------------
DROP TABLE IF EXISTS `role_rule`;
CREATE TABLE `role_rule`  (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `rule_id` int(11) NOT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_rule
-- ----------------------------
INSERT INTO `role_rule` VALUES (1, 1);

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

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

-- ----------------------------
-- Records of rule
-- ----------------------------
INSERT INTO `rule` VALUES (1, 'all');
INSERT INTO `rule` VALUES (2, 'private');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户姓名',
  `alarm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '警号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', 'admin', 1);
INSERT INTO `user` VALUES (4, 'wxq', 'wxq', 'wxq', 2);
INSERT INTO `user` VALUES (5, 'test', 'test', 'test', 2);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (4, 2);

SET FOREIGN_KEY_CHECKS = 1;
