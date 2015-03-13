/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50022
Source Host           : localhost:3306
Source Database       : advisor

Target Server Type    : MYSQL
Target Server Version : 50022
File Encoding         : 65001

Date: 2015-03-13 08:53:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dictionary
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `ID` int(11) NOT NULL auto_increment,
  `kind` varchar(50) default NULL,
  `code` varchar(50) default NULL,
  `title` varchar(50) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO `dictionary` VALUES ('4', 'APP_DICT_INGRED', 'protein', '蛋白质');
INSERT INTO `dictionary` VALUES ('5', 'APP_DICT_INGRED', 'fat', '脂肪');
INSERT INTO `dictionary` VALUES ('6', 'APP_DICT_INGRED', 'dietary_fiber', '膳食纤维');
INSERT INTO `dictionary` VALUES ('7', 'APP_DICT_INGRED', 'carbohydrate', '碳水化合物');
INSERT INTO `dictionary` VALUES ('8', 'APP_DICT_INGRED', 'retinol_equivalent', '视黄醇当量');
INSERT INTO `dictionary` VALUES ('9', 'APP_DICT_INGRED', 'vb1', '硫胺素');
INSERT INTO `dictionary` VALUES ('10', 'APP_DICT_INGRED', 'vb2', '核黄素');
INSERT INTO `dictionary` VALUES ('11', 'APP_DICT_INGRED', 'vpp', '烟酸');
INSERT INTO `dictionary` VALUES ('12', 'APP_DICT_INGRED', 'vitamin_a', '维他命A');
INSERT INTO `dictionary` VALUES ('13', 'APP_DICT_INGRED', 'vitamin_b1', '维他命B1');
INSERT INTO `dictionary` VALUES ('14', 'APP_DICT_INGRED', 'vitamin_b2', '维他命B2');
INSERT INTO `dictionary` VALUES ('15', 'APP_DICT_INGRED', 'vitamin_b6', '维他命B6');
INSERT INTO `dictionary` VALUES ('16', 'APP_DICT_INGRED', 'vitamin_b12', '维他命B12');
INSERT INTO `dictionary` VALUES ('17', 'APP_DICT_INGRED', 'vitamin_c', '维他命C');
INSERT INTO `dictionary` VALUES ('18', 'APP_DICT_INGRED', 'vitamin_d', '维他命D');
INSERT INTO `dictionary` VALUES ('19', 'APP_DICT_INGRED', 'vitamin_e', '维他命E');
INSERT INTO `dictionary` VALUES ('20', 'APP_DICT_INGRED', 'vitamin_k', '维他命K');
INSERT INTO `dictionary` VALUES ('21', 'APP_DICT_INGRED', 'natrium', '钠');
INSERT INTO `dictionary` VALUES ('22', 'APP_DICT_INGRED', 'calcium', '钙');
INSERT INTO `dictionary` VALUES ('23', 'APP_DICT_INGRED', 'ferrum', '铁');
INSERT INTO `dictionary` VALUES ('24', 'APP_DICT_INGRED', 'vc', '抗坏血酸');
INSERT INTO `dictionary` VALUES ('25', 'APP_DICT_INGRED', 'cholestenone', '胆固醇');
INSERT INTO `dictionary` VALUES ('26', 'APP_DICT_INGRED', 'phosphorus', '磷');
INSERT INTO `dictionary` VALUES ('27', 'APP_DICT_INGRED', 'potassium', '钾');
INSERT INTO `dictionary` VALUES ('28', 'APP_DICT_INGRED', 'magnesium', '镁');
INSERT INTO `dictionary` VALUES ('29', 'APP_DICT_INGRED', 'zinc', '锌');
