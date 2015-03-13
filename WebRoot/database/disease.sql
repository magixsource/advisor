/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : advisor

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2015-03-13 08:53:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for disease
-- ----------------------------
DROP TABLE IF EXISTS `disease`;
CREATE TABLE `disease` (
  `ID` int(11) NOT NULL auto_increment,
  `NAME` varchar(100) default NULL COMMENT '疾病名称',
  `DEPT` varchar(11) default NULL COMMENT '科',
  `summary` varchar(2000) default NULL COMMENT '简介',
  `Treatment` text COMMENT '治疗办法',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
