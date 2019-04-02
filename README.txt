springboot+mybatis+mysql jdk1.8 搭建资料管理平台

暂时数据库脚本如下：
sql:
CREATE TABLE `icloud`.`Untitled`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `name` varchar(255) NOT NULL COMMENT '用户姓名',
  `alarm` varchar(255) NOT NULL COMMENT '警号',
  PRIMARY KEY (`id`)
);
