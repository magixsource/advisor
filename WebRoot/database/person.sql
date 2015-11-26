/*
Navicat MySQL Data Transfer

Source Server         : LocalHost
Source Server Version : 50538
Source Host           : localhost:3306
Source Database       : advisor

Target Server Type    : MYSQL
Target Server Version : 50538
File Encoding         : 65001

Date: 2015-11-26 09:17:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `person`
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET gbk NOT NULL,
  `relationship` varchar(100) CHARACTER SET gbk NOT NULL,
  `disease` text CHARACTER SET gbk NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;