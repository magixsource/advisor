/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : advisor

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2015-03-13 08:52:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for principle
-- ----------------------------
DROP TABLE IF EXISTS `principle`;
CREATE TABLE `principle` (
  `id` int(11) NOT NULL auto_increment,
  `disease_id` int(11) default NULL,
  `rule_more` text,
  `rule_less` text,
  `rule_no` text,
  PRIMARY KEY  (`id`),
  KEY `fk_principle` (`disease_id`),
  CONSTRAINT `fk_principle` FOREIGN KEY (`disease_id`) REFERENCES `disease` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
