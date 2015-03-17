/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : advisor

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2015-03-17 10:03:00
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
INSERT INTO `dictionary_kind` VALUES ('3', 'APP_DICT_DISEASE_DEPT', '疾病分类', null);
INSERT INTO `dictionary_kind` VALUES ('4', 'APP_DICT_FOOD_KIND', '食品分类', null);
