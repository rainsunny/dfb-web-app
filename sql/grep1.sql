/*
Navicat MySQL Data Transfer

Source Server         : .
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2013-06-30 15:17:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `adminuserinfo`
-- ----------------------------
DROP TABLE IF EXISTS `adminuserinfo`;
CREATE TABLE `adminuserinfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) CHARACTER SET utf8 NOT NULL,
  `level` int(11) NOT NULL,
  `areaId` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_adminUserInfo_AreaInfo_areaId` (`areaId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of adminuserinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `assignmentinfo`
-- ----------------------------
DROP TABLE IF EXISTS `assignmentinfo`;
CREATE TABLE `assignmentinfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '转让Id',
  `title` varchar(20) NOT NULL COMMENT '转让信息标题',
  `content` varchar(20) NOT NULL COMMENT '转让信息描述',
  `price` varchar(20) NOT NULL COMMENT '价格',
  `address` varchar(20) DEFAULT NULL COMMENT '地址',
  `linkman` varchar(20) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `releasetime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '发布时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of assignmentinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `cityinfo`
-- ----------------------------
DROP TABLE IF EXISTS `cityinfo`;
CREATE TABLE `cityinfo` (
  `cityID` int(11) NOT NULL AUTO_INCREMENT,
  `provincialID` int(11) DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`cityID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of cityinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `countyinfo`
-- ----------------------------
DROP TABLE IF EXISTS `countyinfo`;
CREATE TABLE `countyinfo` (
  `countyID` int(11) NOT NULL AUTO_INCREMENT,
  `provincialID` int(11) NOT NULL,
  `cityId` int(11) NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 NOT NULL,
  `wapUrl` varchar(100) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`countyID`),
  KEY `FK_countyInfo_provincialInfo_provincialID` (`provincialID`),
  KEY `FK_countyInfo_cityInfo_cityID` (`cityId`),
  CONSTRAINT `FK_countyInfo_cityInfo_cityID` FOREIGN KEY (`cityId`) REFERENCES `cityinfo` (`cityID`) ON DELETE NO ACTION,
  CONSTRAINT `FK_countyInfo_provincialInfo_provincialID` FOREIGN KEY (`provincialID`) REFERENCES `provincialinfo` (`provincialID`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of countyinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `dfb_advertising`
-- ----------------------------
DROP TABLE IF EXISTS `dfb_advertising`;
CREATE TABLE `dfb_advertising` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `content` varchar(500) DEFAULT NULL,
  `website` varchar(200) DEFAULT NULL,
  `CountID` int(11) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='status:0代表文本，1代表网页';

-- ----------------------------
-- Records of dfb_advertising
-- ----------------------------

-- ----------------------------
-- Table structure for `dfb_catgroup_map`
-- ----------------------------
DROP TABLE IF EXISTS `dfb_catgroup_map`;
CREATE TABLE `dfb_catgroup_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `updatetime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '大组下面的小组更新时间',
  PRIMARY KEY (`id`),
  KEY `cat_id` (`cat_id`),
  KEY `cgroup_id` (`group_id`)
) ENGINE=MyISAM AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dfb_catgroup_map
-- ----------------------------

-- ----------------------------
-- Table structure for `dfb_groupmerchant_map`
-- ----------------------------
DROP TABLE IF EXISTS `dfb_groupmerchant_map`;
CREATE TABLE `dfb_groupmerchant_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) NOT NULL,
  `merchant_id` int(11) NOT NULL,
  `updatetime` datetime DEFAULT '2013-04-09 01:02:03' COMMENT '小组下面的商家更新时间',
  PRIMARY KEY (`id`),
  KEY `merchant_id` (`merchant_id`),
  KEY `mgroup_id` (`group_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2346 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `dfb_merchant_cat`
-- ----------------------------
DROP TABLE IF EXISTS `dfb_merchant_cat`;
CREATE TABLE `dfb_merchant_cat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `cat_image` varchar(100) NOT NULL DEFAULT 'null.jpg' COMMENT '大分组图片',
  `updatetime` datetime NOT NULL DEFAULT '2013-05-30 03:01:03' COMMENT '分组修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `dfb_merchant_comment`
-- ----------------------------
DROP TABLE IF EXISTS `dfb_merchant_comment`;
CREATE TABLE `dfb_merchant_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `content` varchar(500) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `f_c_merchantid` (`mid`),
  KEY `f_c_userid` (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=2468 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `dfb_merchant_group`
-- ----------------------------
DROP TABLE IF EXISTS `dfb_merchant_group`;
CREATE TABLE `dfb_merchant_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `countyID` int(11) NOT NULL DEFAULT '0',
  `gorder` int(11) NOT NULL DEFAULT '0',
  `updatetime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '小分组修改时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=467 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `dfb_merchant_image`
-- ----------------------------
DROP TABLE IF EXISTS `dfb_merchant_image`;
CREATE TABLE `dfb_merchant_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(200) NOT NULL,
  `mid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `mi_mid` (`mid`)
) ENGINE=MyISAM AUTO_INCREMENT=1969 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `dfb_weibo_copy`
-- ----------------------------
DROP TABLE IF EXISTS `dfb_weibo_copy`;
CREATE TABLE `dfb_weibo_copy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) NOT NULL,
  `time` datetime NOT NULL,
  `up` int(11) DEFAULT '0',
  `down` int(11) DEFAULT '0',
  `share` int(11) DEFAULT '0',
  `uid` int(11) NOT NULL,
  `gid` int(11) NOT NULL,
  `areacode` varchar(11) NOT NULL,
  `last_reply` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `gid` (`gid`),
  KEY `w_uid` (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=4174 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dfb_weibo_copy
-- ----------------------------

-- ----------------------------
-- Table structure for `dfb_weibo_reply`
-- ----------------------------
DROP TABLE IF EXISTS `dfb_weibo_reply`;
CREATE TABLE `dfb_weibo_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `replycontent` varchar(150) NOT NULL,
  `time` datetime NOT NULL,
  `uid` int(11) NOT NULL,
  `wid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `wid` (`wid`),
  KEY `wr_uid` (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=12190 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dfb_weibo_reply
-- ----------------------------

-- ----------------------------
-- Table structure for `hireinfo`
-- ----------------------------
DROP TABLE IF EXISTS `hireinfo`;
CREATE TABLE `hireinfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '出租Id',
  `title` varchar(20) NOT NULL COMMENT '出租信息标题',
  `address` varchar(20) NOT NULL COMMENT '地址',
  `price` varchar(20) NOT NULL COMMENT '价格',
  `house_type` varchar(20) NOT NULL COMMENT '户型',
  `floor` varchar(20) DEFAULT NULL COMMENT '楼层',
  `cell_name` varchar(20) DEFAULT NULL COMMENT '小区名称',
  `configuration` varchar(20) NOT NULL COMMENT '房屋配置',
  `linkman` varchar(20) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `releasetime` datetime NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hireinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `infocommentlist`
-- ----------------------------
DROP TABLE IF EXISTS `infocommentlist`;
CREATE TABLE `infocommentlist` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `typecode` int(11) NOT NULL COMMENT '类型码',
  `uid` int(11) NOT NULL COMMENT '发布者用户ID',
  `otherid` int(11) NOT NULL COMMENT '评论者用户Id',
  `commentContent` varchar(255) NOT NULL COMMENT '评论内容',
  `commentTime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '评论时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of infocommentlist
-- ----------------------------

-- ----------------------------
-- Table structure for `jobinfo`
-- ----------------------------
DROP TABLE IF EXISTS `jobinfo`;
CREATE TABLE `jobinfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '招聘Id',
  `title` varchar(20) NOT NULL COMMENT '招聘信息标题',
  `unitname` varchar(20) NOT NULL COMMENT '单位名称',
  `job` varchar(20) NOT NULL COMMENT '职位',
  `claim` varchar(20) DEFAULT NULL COMMENT '要求',
  `pay` varchar(20) NOT NULL COMMENT '工资待遇',
  `address` varchar(20) DEFAULT NULL COMMENT '地址',
  `linkman` varchar(20) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `releasetime` datetime NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `merchantinfo`
-- ----------------------------
DROP TABLE IF EXISTS `merchantinfo`;
CREATE TABLE `merchantinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `major` varchar(200) DEFAULT NULL,
  `introduction` varchar(500) NOT NULL,
  `news` varchar(400) DEFAULT NULL,
  `address` varchar(100) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `isrecommend` tinyint(1) NOT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `up` int(11) NOT NULL,
  `countyID` int(16) NOT NULL,
  `last_reply` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '最后评论时间',
  `updatetime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '最后修改时间',
  `time` datetime NOT NULL DEFAULT '2013-04-09 01:02:03',
  `log` varchar(100) NOT NULL DEFAULT 'null.jpg' COMMENT '商家log',
  `seecount` int(11) NOT NULL DEFAULT '0' COMMENT '浏览量',
  `share` int(11) NOT NULL DEFAULT '0' COMMENT '分享次数',
  `state` tinyint(1) DEFAULT '1' COMMENT '0表示禁用，1表示启用',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1669 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `merchantseelist`
-- ----------------------------
DROP TABLE IF EXISTS `merchantseelist`;
CREATE TABLE `merchantseelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '浏览id',
  `merchantid` int(11) NOT NULL COMMENT '商户id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `seetime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '浏览时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1379 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `provincialinfo`
-- ----------------------------
DROP TABLE IF EXISTS `provincialinfo`;
CREATE TABLE `provincialinfo` (
  `provincialID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`provincialID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;


-- ----------------------------
-- Table structure for `secondhandinfo`
-- ----------------------------
DROP TABLE IF EXISTS `secondhandinfo`;
CREATE TABLE `secondhandinfo` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '转让Id',
  `title` varchar(20) NOT NULL COMMENT '转让信息标题',
  `product_name` varchar(20) NOT NULL COMMENT '商品名称',
  `price` varchar(20) NOT NULL COMMENT '价格',
  `detailcontent` varchar(20) DEFAULT NULL COMMENT '详细信息描述',
  `linkman` varchar(20) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `releasetime` datetime NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `userimagelist`
-- ----------------------------
DROP TABLE IF EXISTS `userimagelist`;
CREATE TABLE `userimagelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户图片列表id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `imageurl` varchar(100) NOT NULL,
  `uptime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '图片上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userimagelist
-- ----------------------------

-- ----------------------------
-- Table structure for `userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `headphoto` varchar(200) DEFAULT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `gender` tinyint(4) NOT NULL DEFAULT '0',
  `phone` varchar(16) DEFAULT NULL,
  `qq` varchar(16) DEFAULT NULL,
  `cash` int(11) NOT NULL,
  `checkin` datetime DEFAULT '0000-00-00 00:00:00',
  `message` varchar(500) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1397 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------

-- ----------------------------
-- Table structure for `usermessagelist`
-- ----------------------------
DROP TABLE IF EXISTS `usermessagelist`;
CREATE TABLE `usermessagelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `uid` int(11) NOT NULL COMMENT '用户id',
  `otherid` int(11) NOT NULL COMMENT '留言者用户id',
  `messagecontent` varchar(20) NOT NULL COMMENT '留言内容',
  `commenttime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '留言时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of usermessagelist
-- ----------------------------

-- ----------------------------
-- Table structure for `userseelist`
-- ----------------------------
DROP TABLE IF EXISTS `userseelist`;
CREATE TABLE `userseelist` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '浏览id',
  `uid` int(11) NOT NULL COMMENT '被浏览用户id',
  `otherid` int(11) NOT NULL COMMENT '其他用户id',
  `wid` int(11) NOT NULL DEFAULT '0',
  `seetime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '浏览时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=752 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userseelist
-- ----------------------------

-- ----------------------------
-- Table structure for `weibocomentreplylist`
-- ----------------------------
DROP TABLE IF EXISTS `weibocomentreplylist`;
CREATE TABLE `weibocomentreplylist` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论回复id',
  `commentid` int(11) NOT NULL COMMENT '评论id',
  `uid` int(11) NOT NULL COMMENT '发表评论的用户的id',
  `replyuid` int(11) NOT NULL COMMENT '回复者用户id',
  `replycontent` varchar(100) NOT NULL COMMENT '回复内容',
  `replyTime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03' COMMENT '回复评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weibocomentreplylist
-- ----------------------------

-- ----------------------------
-- Table structure for `weiboinfo`
-- ----------------------------
DROP TABLE IF EXISTS `weiboinfo`;
CREATE TABLE `weiboinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) DEFAULT NULL,
  `time` datetime NOT NULL,
  `up` int(11) DEFAULT '0',
  `down` int(11) DEFAULT '0',
  `share` int(11) DEFAULT '0',
  `uid` int(11) NOT NULL,
  `countyID` int(11) NOT NULL,
  `last_reply` datetime NOT NULL,
  `photo` varchar(30) DEFAULT NULL COMMENT '上传的图片',
  `updatetime` datetime NOT NULL DEFAULT '2013-04-09 01:02:03',
  `seecount` int(11) NOT NULL DEFAULT '0' COMMENT '浏览量',
  PRIMARY KEY (`id`),
  KEY `w_uid` (`uid`)
) ENGINE=MyISAM AUTO_INCREMENT=4268 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of weiboinfo
-- ----------------------------

-- ----------------------------
-- Procedure structure for `test_multi_sets`
-- ----------------------------
DROP PROCEDURE IF EXISTS `test_multi_sets`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `test_multi_sets`()
    DETERMINISTIC
begin
        select user() as first_col;
        select user() as first_col, now() as second_col;
        select user() as first_col, now() as second_col, now() as third_col;
        end
;;
DELIMITER ;
