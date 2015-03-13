/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : advisor

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2015-03-13 08:51:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(50) default NULL,
  `orderby` int(11) default NULL,
  `url` varchar(200) default NULL,
  `admin` tinyint(1) default NULL COMMENT 'is admin menu',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '主页', '1', '/', null);
INSERT INTO `menu` VALUES ('2', '字典', '2', '/dictionary/list', '1');
INSERT INTO `menu` VALUES ('3', '食品', '3', '/ingredient/list', null);
INSERT INTO `menu` VALUES ('4', '疾病', '4', '/disease/list', null);
