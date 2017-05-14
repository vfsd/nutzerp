/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50151
Source Host           : localhost:3306
Source Database       : erp

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2017-05-02 15:53:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_accountant
-- ----------------------------
DROP TABLE IF EXISTS `t_accountant`;
CREATE TABLE `t_accountant` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  `workShift` datetime DEFAULT NULL,
  `closeShift` datetime DEFAULT NULL,
  `timeGroup` varchar(255) DEFAULT NULL,
  `state` int(255) unsigned zerofill DEFAULT NULL,
  `windowNum` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_accountant
-- ----------------------------
INSERT INTO `t_accountant` VALUES ('1', 'Jack', '123456', '2017-04-30 07:50:49', '2017-04-30 18:51:00', '13:45-14:00/15:15-15:30/12:15-12:30/16:00-16:15/10:30-10:45/14:30-14:45/15:30-15:45/11:30-11:45/11:00-11:15/16:45-17:00/9:45-10:00/8:00-8:15', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '1');
INSERT INTO `t_accountant` VALUES ('2', 'Neck', '123456', '2017-04-30 19:13:54', '2017-04-30 19:14:01', '9:00-9:15/14:30-14:45/15:30-15:45', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001', '2');
INSERT INTO `t_accountant` VALUES ('6', 'Java1256', '123456', null, null, 'null/12:15-12:30/15:00-15:15/13:30-13:45/11:45-12:00', '000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000', '0');

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'Peak', '123456');

-- ----------------------------
-- Table structure for t_appointmentmsg
-- ----------------------------
DROP TABLE IF EXISTS `t_appointmentmsg`;
CREATE TABLE `t_appointmentmsg` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `content` varchar(128) DEFAULT NULL,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `state` int(32) DEFAULT NULL,
  `userId` int(32) DEFAULT NULL,
  `accountantId` int(32) DEFAULT NULL,
  `timeGroup` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_appointmentmsg
-- ----------------------------
INSERT INTO `t_appointmentmsg` VALUES ('1', 'xxxxxxxxxxxIKOKOKO', null, null, '0', '1', '2', '/12:15-12:30/16:00-16:15');
INSERT INTO `t_appointmentmsg` VALUES ('2', 'This thing is very importment', null, null, '1', '1', '1', '/12:15-12:30/10:30-10:45');
INSERT INTO `t_appointmentmsg` VALUES ('3', 'Need hdskf bjh  jsdhfasdf ', null, null, '2', '2', '1', '/14:15-14:30/16:45-17:00');
INSERT INTO `t_appointmentmsg` VALUES ('5', 'fdghdfhfgh vbvcbvcx', null, null, '1', '2', '1', '/12:15-12:30/16:00-16:15/10:30-10:45');
INSERT INTO `t_appointmentmsg` VALUES ('6', '报销飞机票，还有送二月份财报', null, null, '0', '1', '2', '/14:30-14:45/15:30-15:45');
INSERT INTO `t_appointmentmsg` VALUES ('7', '工资结算', null, null, '0', '2', '1', '/14:30-14:45/15:30-15:45/11:30-11:45/11:00-11:15');
INSERT INTO `t_appointmentmsg` VALUES ('8', 'cccccccccccccc', null, null, '1', '1', '1', '/16:45-17:00');
INSERT INTO `t_appointmentmsg` VALUES ('9', 'Cook look book nook', null, null, '0', '1', '1', '/8:30-8:45/9:15-9:30');
INSERT INTO `t_appointmentmsg` VALUES ('10', 'Please accept my reservation request OK  nj  vbv', null, null, '1', '1', '1', '/13:00-13:15/17:45-18:00');
INSERT INTO `t_appointmentmsg` VALUES ('11', '', null, null, '0', '1', '5', '/16:15-16:30/14:30-14:45/13:00-13:15/11:30-11:45');
INSERT INTO `t_appointmentmsg` VALUES ('12', 'bzxhchvjzxcvjhzxcbn', null, null, '1', '1', '6', '/12:15-12:30/15:00-15:15/13:30-13:45/11:45-12:00');

-- ----------------------------
-- Table structure for t_news
-- ----------------------------
DROP TABLE IF EXISTS `t_news`;
CREATE TABLE `t_news` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `content` varchar(128) DEFAULT NULL,
  `state` int(32) DEFAULT NULL,
  `accountId` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_news
-- ----------------------------
INSERT INTO `t_news` VALUES ('1', '1号下午系统升级，放假半天', '0', '1');
INSERT INTO `t_news` VALUES ('2', 'java session is very good gdsfgdsfgdfsgdsfgdsfgdsfgdsfgdfsg', '0', '1');
INSERT INTO `t_news` VALUES ('3', 'bbbbbbbbbbbbbbbbbbbbbbbb', '0', '1');
INSERT INTO `t_news` VALUES ('5', 'my first news', '0', '6');
INSERT INTO `t_news` VALUES ('6', 'my second news', '0', '6');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `password` varchar(128) DEFAULT NULL,
  `state` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '123456', '0');
INSERT INTO `t_user` VALUES ('2', 'java', '123456', '0');
INSERT INTO `t_user` VALUES ('3', 'admin1', '123456', '1');
INSERT INTO `t_user` VALUES ('5', 'Java12', '123456', '1');
