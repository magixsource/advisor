/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : advisor

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2015-03-13 08:53:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dictionary_kind
-- ----------------------------
DROP TABLE IF EXISTS `dictionary_kind`;
CREATE TABLE `dictionary_kind` (
  `ID` int(11) NOT NULL auto_increment,
  `kind` varchar(50) default NULL,
  `title` varchar(50) default NULL,
  `parentid` int(11) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dictionary_kind
-- ----------------------------
INSERT INTO `dictionary_kind` VALUES ('2', 'APP_DICT_INGRED', '食品成分', null);
