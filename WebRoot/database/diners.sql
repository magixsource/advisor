/*
Navicat MySQL Data Transfer

Source Server         : LocalHost
Source Server Version : 50538
Source Host           : localhost:3306
Source Database       : advisor

Target Server Type    : MYSQL
Target Server Version : 50538
File Encoding         : 65001

Date: 2015-11-26 10:03:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `diners`
-- ----------------------------
DROP TABLE IF EXISTS `diners`;
CREATE TABLE `diners` (
  `partyid` int(11) NOT NULL,
  `personid` int(11) NOT NULL,
  PRIMARY KEY (`partyid`,`personid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;