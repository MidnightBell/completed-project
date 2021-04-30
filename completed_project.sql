/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 80003
Source Host           : localhost:3306
Source Database       : completed_project

Target Server Type    : MYSQL
Target Server Version : 80003
File Encoding         : 65001

Date: 2021-04-30 15:12:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `per_name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `per_value` varchar(255) DEFAULT NULL COMMENT '权限值',
  `per_type` int(11) DEFAULT NULL COMMENT '权限类型（1：按钮权限 2：菜单权限）',
  `parent_id` int(11) DEFAULT '0' COMMENT '父权限id',
  `per_description` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `menu_comonent` varchar(255) DEFAULT NULL COMMENT '菜单地址',
  `is_del` int(255) DEFAULT '0' COMMENT '是否删除（0：未删除 1：删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '后台管理', 'management', '1', '0', '', '/management', '0');
INSERT INTO `t_permission` VALUES ('2', '用户管理', 'userManagement', '1', '1', '', '/management/userManagement', '0');
INSERT INTO `t_permission` VALUES ('3', '角色管理', 'roleManagement', '1', '1', null, '/management/roleManagement', '0');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL COMMENT '角色名字（唯一不重复）',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人id',
  `update_user` int(11) DEFAULT NULL COMMENT '修改人id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_del` int(1) DEFAULT '0' COMMENT '是否删除 1：删除 0：未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '管理员', '1', null, '2021-04-29 14:28:40', '2021-04-29 14:28:40', '0');
INSERT INTO `t_role` VALUES ('2', '测试角色', '1', null, '2021-04-29 14:41:00', '2021-04-29 14:41:00', '0');
INSERT INTO `t_role` VALUES ('3', '测试角色001', '1', null, '2021-04-29 14:41:18', '2021-04-29 14:41:18', '0');

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('1', '1', '1');
INSERT INTO `t_role_permission` VALUES ('2', '1', '2');
INSERT INTO `t_role_permission` VALUES ('3', '1', '3');
INSERT INTO `t_role_permission` VALUES ('4', '2', '1');
INSERT INTO `t_role_permission` VALUES ('5', '2', '2');
INSERT INTO `t_role_permission` VALUES ('6', '2', '3');
INSERT INTO `t_role_permission` VALUES ('8', '3', '1');
INSERT INTO `t_role_permission` VALUES ('9', '3', '2');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(16) NOT NULL COMMENT '用户名（唯一不重复）',
  `password` varchar(16) NOT NULL COMMENT '密码',
  `phone` varchar(25) DEFAULT NULL COMMENT '电话',
  `nick_name` varchar(30) DEFAULT NULL COMMENT '昵称',
  `sex` varchar(2) DEFAULT NULL COMMENT '性别',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人id（0：用户自行注册）',
  `update_user` int(11) DEFAULT NULL COMMENT '修改人id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_del` int(255) DEFAULT '0' COMMENT '是否删除 1：删除 0：未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', 'admin123456', '18392003880', '管理员账号', null, '0', null, '2021-04-29 09:20:32', '2021-04-29 09:20:32', '0');
INSERT INTO `t_user` VALUES ('3', 'zhongsheng', 'BellZS951003', '18392003880', '夜伴钟声', '男', '1', null, '2021-04-29 10:36:58', '2021-04-29 10:36:58', '0');
INSERT INTO `t_user` VALUES ('4', 'test001', 'Test0001', '10086', '测试账号', '男', '0', null, '2021-04-29 14:36:27', '2021-04-29 14:36:27', '0');
INSERT INTO `t_user` VALUES ('5', 'test002', 'Test0002', '10086', '测试账号', '男', '1', null, '2021-04-29 14:38:19', '2021-04-29 14:38:19', '0');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='用户角色关系';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '1');
INSERT INTO `t_user_role` VALUES ('2', '1', '2');
INSERT INTO `t_user_role` VALUES ('3', '1', '3');
INSERT INTO `t_user_role` VALUES ('6', '3', '1');
INSERT INTO `t_user_role` VALUES ('7', '3', '2');
INSERT INTO `t_user_role` VALUES ('8', '3', '3');
