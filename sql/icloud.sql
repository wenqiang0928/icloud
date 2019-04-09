/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : icloud

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 09/04/2019 23:44:50
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
) ENGINE = InnoDB AUTO_INCREMENT = 72 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of docs
-- ----------------------------
INSERT INTO `docs` VALUES (1, 'admin', 1, '0', NULL, NULL, '/admin', 0, 1, '2019-04-06 03:10:00', 0, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (2, '1', 1, '0', NULL, NULL, '/admin/1', 1, 1, '2019-04-06 03:10:00', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (3, '2', 1, '0', NULL, NULL, '/admin/2', 1, 1, '2019-04-06 03:10:03', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (4, '3', 1, '0', NULL, NULL, '/admin/3', 1, 1, '2019-04-06 03:10:05', 0, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (5, '1', 1, '0', NULL, NULL, '/admin/1/1', 2, 1, '2019-04-06 03:10:20', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (6, '2', 1, '0', NULL, NULL, '/admin/1/2', 2, 1, '2019-04-06 03:10:23', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (7, '3', 1, '0', NULL, NULL, '/admin/1/3', 2, 1, '2019-04-06 03:10:25', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (8, '3', 1, '0', NULL, NULL, '/admin/1/1/3', 5, 1, '2019-04-06 03:10:35', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (9, 'a', 1, '0', NULL, NULL, '/admin/1/1/a', 5, 1, '2019-04-06 03:10:38', 1, NULL, NULL, NULL);
INSERT INTO `docs` VALUES (41, '对对对', 1, '0', NULL, NULL, '/admin/3/对对对', 4, 1, '2019-04-08 07:52:45', 0, NULL, '2019-04-08 07:52:45', NULL);
INSERT INTO `docs` VALUES (42, 'log-info-2018-12-19.0.log', 2, '211638', 'log', 'cc40e8f733609491cdf81f51710fb0df', '/admin/3/对对对/log-info-2018-12-19.0.log', 41, 1, '2019-04-08 07:52:59', 0, NULL, '2019-04-08 07:52:59', NULL);
INSERT INTO `docs` VALUES (43, 'log-info-2018-12-05.0.log', 2, '17.6 KB', 'log', '32f99a6d4931c194a84e4c1c12f4c54b', '/admin/3/对对对/log-info-2018-12-05.0.log', 41, 1, '2019-04-08 08:02:16', 0, NULL, '2019-04-08 08:02:16', NULL);
INSERT INTO `docs` VALUES (44, 'log-info-2018-12-04.0.log', 2, '107 KB', 'log', '05f9103a0143bed051aaea60cf7af896', '/admin/3/对对对/log-info-2018-12-04.0.log', 41, 1, '2019-04-08 08:03:43', 1, '333', '2019-04-08 08:03:43', '4454');
INSERT INTO `docs` VALUES (45, 'test', 1, '0', NULL, NULL, '/test', 0, 5, '2019-04-08 08:36:05', 0, NULL, '2019-04-08 08:36:05', NULL);
INSERT INTO `docs` VALUES (46, 'test', 1, '0', NULL, NULL, '/test/test', 45, 5, '2019-04-08 08:36:11', 0, NULL, '2019-04-08 08:36:11', NULL);
INSERT INTO `docs` VALUES (47, 'log-warn-2018-12-19.0.log', 2, '127 B', 'log', '072fbff73780aa54542c1659639bf8b7', '/test/test/log-warn-2018-12-19.0.log', 46, 5, '2019-04-08 08:51:17', 0, NULL, '2019-04-08 08:51:17', NULL);
INSERT INTO `docs` VALUES (48, 'P001.jpg', 2, '597 KB', 'jpg', '62519e9afcca4a8a7d31b376d4501a34', '/admin/P001.jpg', 1, 1, '2019-04-08 12:55:19', 0, NULL, '2019-04-08 12:55:19', NULL);
INSERT INTO `docs` VALUES (49, 'wxq', 1, '0', NULL, NULL, '/wxq', 0, 4, '2019-04-09 13:09:11', 0, NULL, '2019-04-09 13:09:11', NULL);
INSERT INTO `docs` VALUES (50, '194_多线程_开篇.mp4', 2, '2.9 MB', 'mp4', '4eb53e8ce86b8625a2267564ea39608d', 'E:/data0/uploads//admin/194_多线程_开篇.mp4', 1, 1, '2019-04-09 13:47:34', 0, NULL, '2019-04-09 13:47:34', NULL);
INSERT INTO `docs` VALUES (51, '195_多线程_概念.mp4', 2, '5.0 MB', 'mp4', '3800a853732be725d08c924c11795178', 'E:/data0/uploads//admin/195_多线程_概念.mp4', 1, 1, '2019-04-09 14:30:42', 0, NULL, '2019-04-09 14:30:42', NULL);
INSERT INTO `docs` VALUES (52, '202_多线程_推导lambda_简化线程.mp4', 2, '5.0 MB', 'mp4', '39188147025d8d41ca76fe1f7d74990a', 'E:/data0/uploads//admin/202_多线程_推导lambda_简化线程.mp4', 1, 1, '2019-04-09 15:13:19', 0, NULL, '2019-04-09 15:13:19', NULL);
INSERT INTO `docs` VALUES (53, '215_多线程_并发_同步_synchronized块.mp4', 2, '250 KB', 'mp4', 'f12298f0e08c77fb5a50662c4e853b53', 'E:/data0/uploads//admin/215_多线程_并发_同步_synchronized块.mp4', 1, 1, '2019-04-09 15:18:23', 0, NULL, '2019-04-09 15:18:23', NULL);
INSERT INTO `docs` VALUES (54, '216_多线程_并发_同步_性能分析.mp4', 2, '5.0 MB', 'mp4', '6f7acbe3cb0bb92fd4d81999ec80fe9c', 'E:/data0/uploads//admin/216_多线程_并发_同步_性能分析.mp4', 1, 1, '2019-04-09 15:19:45', 0, NULL, '2019-04-09 15:19:45', NULL);
INSERT INTO `docs` VALUES (55, '214_多线程_并发_同步_synchronized方法.mp4', 2, '4.9 MB', 'mp4', '8261c14edfcdd6065a56e5e848b53fd5', 'E:/data0/uploads//admin/214_多线程_并发_同步_synchronized方法.mp4', 1, 1, '2019-04-09 15:20:21', 0, NULL, '2019-04-09 15:20:21', NULL);
INSERT INTO `docs` VALUES (56, 'LambdaTest01.java', 2, '1.1 KB', 'java', '2eee0c0378362bbce60b914b1fd8bb12', 'E:/data0/uploads//admin/LambdaTest01.java', 1, 1, '2019-04-09 15:27:49', 0, NULL, '2019-04-09 15:27:49', NULL);
INSERT INTO `docs` VALUES (57, 'LambdaTest02.java', 2, '764 B', 'java', '004102d76dabfa1e957d5034889939cb', 'E:/data0/uploads//admin/LambdaTest02.java', 1, 1, '2019-04-09 15:40:08', 0, NULL, '2019-04-09 15:40:08', NULL);
INSERT INTO `docs` VALUES (58, 'LambdaTest03.java', 2, '861 B', 'java', 'cfac07cba897d7ba47bcc9fdef028838', 'E:/data0/uploads//admin/LambdaTest03.java', 1, 1, '2019-04-09 15:40:09', 0, NULL, '2019-04-09 15:40:09', NULL);
INSERT INTO `docs` VALUES (59, 'LambdaTest04.java', 2, '367 B', 'java', '583b40960117fce6e6174624aaf27159', 'E:/data0/uploads//admin/LambdaTest04.java', 1, 1, '2019-04-09 15:40:09', 0, NULL, '2019-04-09 15:40:09', NULL);
INSERT INTO `docs` VALUES (60, 'LambdaThread.java', 2, '1003 B', 'java', '95d9211ebe634fe11474e7600a35206e', 'E:/data0/uploads//admin/LambdaThread.java', 1, 1, '2019-04-09 15:40:09', 0, NULL, '2019-04-09 15:40:09', NULL);
INSERT INTO `docs` VALUES (61, 'StaticProxy.java', 2, '950 B', 'java', '565ccc1fb2eccb922454b9804a589b20', 'E:/data0/uploads//admin/StaticProxy.java', 1, 1, '2019-04-09 15:42:08', 0, NULL, '2019-04-09 15:42:08', NULL);
INSERT INTO `docs` VALUES (62, 'TDownloader.java', 2, '863 B', 'java', '1eacd6ab225124e078c42e38e748e9cb', 'E:/data0/uploads//admin/TDownloader.java', 1, 1, '2019-04-09 15:42:08', 0, NULL, '2019-04-09 15:42:08', NULL);
INSERT INTO `docs` VALUES (63, 'Web12306.java', 2, '775 B', 'java', '05c7e1fa6f3d2b48fda5257e6413ca42', 'E:/data0/uploads//admin/Web12306.java', 1, 1, '2019-04-09 15:42:08', 0, NULL, '2019-04-09 15:42:08', NULL);
INSERT INTO `docs` VALUES (64, 'WebDownloader.java', 2, '672 B', 'java', '6a545b7b4733ed019777579e03b413cf', 'E:/data0/uploads//admin/WebDownloader.java', 1, 1, '2019-04-09 15:42:08', 0, NULL, '2019-04-09 15:42:08', NULL);
INSERT INTO `docs` VALUES (65, 'CRacer.class', 2, '2.7 KB', 'class', 'c961a8dde574ee83484a53ace46232bc', 'E:/data0/uploads//admin/3/对对对/CRacer.class', 41, 1, '2019-04-09 15:42:31', 0, NULL, '2019-04-09 15:42:31', NULL);
INSERT INTO `docs` VALUES (66, 'IDownloader.class', 2, '1.4 KB', 'class', 'd21570ffa7a49c029ef271f2d88ec5a0', 'E:/data0/uploads//admin/3/对对对/IDownloader.class', 41, 1, '2019-04-09 15:42:31', 0, NULL, '2019-04-09 15:42:31', NULL);
INSERT INTO `docs` VALUES (67, 'IInterest.class', 2, '142 B', 'class', 'b416645cc9a25ca13a0eb29e8ff075bc', 'E:/data0/uploads//admin/3/对对对/IInterest.class', 41, 1, '2019-04-09 15:42:32', 0, NULL, '2019-04-09 15:42:32', NULL);
INSERT INTO `docs` VALUES (68, 'ILike.class', 2, '136 B', 'class', 'd464fc9657ee99dceec7b61450867e8a', 'E:/data0/uploads//admin/3/对对对/ILike.class', 41, 1, '2019-04-09 15:42:32', 0, NULL, '2019-04-09 15:42:32', NULL);
INSERT INTO `docs` VALUES (69, 'ILove.class', 2, '137 B', 'class', '063f39378c90635a7146853cc9fc4b17', 'E:/data0/uploads//admin/3/对对对/ILove.class', 41, 1, '2019-04-09 15:42:32', 0, NULL, '2019-04-09 15:42:32', NULL);
INSERT INTO `docs` VALUES (70, 'Interest.class', 2, '739 B', 'class', '61807c86f1e7950856bd7658011d9cbd', 'E:/data0/uploads//admin/3/对对对/Interest.class', 41, 1, '2019-04-09 15:42:32', 0, NULL, '2019-04-09 15:42:32', NULL);
INSERT INTO `docs` VALUES (71, 'LambdaTest01$1.class', 2, '686 B', 'class', '6820a9270a95ad3d37726f9be197245a', 'E:/data0/uploads//admin/3/对对对/LambdaTest01$1.class', 41, 1, '2019-04-09 15:42:32', 0, NULL, '2019-04-09 15:42:32', NULL);

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

-- ----------------------------
-- Records of rule
-- ----------------------------
INSERT INTO `rule` VALUES (1, 'all');
INSERT INTO `rule` VALUES (2, 'private');

-- ----------------------------
-- Table structure for suffix_manage
-- ----------------------------
DROP TABLE IF EXISTS `suffix_manage`;
CREATE TABLE `suffix_manage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展名',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型 1图片 2文档 3视频 4音频 0其他',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_unique`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of suffix_manage
-- ----------------------------
INSERT INTO `suffix_manage` VALUES (1, '.bmp', 1);
INSERT INTO `suffix_manage` VALUES (2, '.jpg', 1);
INSERT INTO `suffix_manage` VALUES (3, '.jpeg', 1);
INSERT INTO `suffix_manage` VALUES (4, '.png', 1);
INSERT INTO `suffix_manage` VALUES (5, '.gif', 1);
INSERT INTO `suffix_manage` VALUES (6, '.doc', 2);
INSERT INTO `suffix_manage` VALUES (7, '.docs', 2);
INSERT INTO `suffix_manage` VALUES (8, '.xls', 2);
INSERT INTO `suffix_manage` VALUES (9, '.xlsx', 2);
INSERT INTO `suffix_manage` VALUES (10, '.ppt', 2);
INSERT INTO `suffix_manage` VALUES (11, '.pptx', 2);
INSERT INTO `suffix_manage` VALUES (12, '.txt', 2);
INSERT INTO `suffix_manage` VALUES (13, '.avi', 3);
INSERT INTO `suffix_manage` VALUES (14, '.mpg', 3);
INSERT INTO `suffix_manage` VALUES (15, '.mlv', 3);
INSERT INTO `suffix_manage` VALUES (16, '.mpe', 3);
INSERT INTO `suffix_manage` VALUES (17, '.mov', 3);
INSERT INTO `suffix_manage` VALUES (18, '.qt', 3);
INSERT INTO `suffix_manage` VALUES (19, '.asf', 3);
INSERT INTO `suffix_manage` VALUES (20, '.rm', 3);
INSERT INTO `suffix_manage` VALUES (21, '.mp4', 3);
INSERT INTO `suffix_manage` VALUES (22, '.rmvb', 3);
INSERT INTO `suffix_manage` VALUES (23, '.mp3', 4);
INSERT INTO `suffix_manage` VALUES (24, '.wav', 4);
INSERT INTO `suffix_manage` VALUES (25, '.wma', 4);
INSERT INTO `suffix_manage` VALUES (26, '.mid', 4);
INSERT INTO `suffix_manage` VALUES (27, '.m4a', 4);

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
