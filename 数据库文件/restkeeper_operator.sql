/*
 Navicat Premium Data Transfer

 Source Server         : restkeeper-128
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 192.168.200.128:3306
 Source Schema         : restkeeper_operator

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 03/08/2020 17:20:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_enterprise_account
-- ----------------------------
DROP TABLE IF EXISTS `t_enterprise_account`;
CREATE TABLE `t_enterprise_account`  (
  `enterprise_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '企业id',
  `enterprise_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '企业名称',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码（后台自动下发）',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商户号（下发生成）',
  `applicant` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '申请人',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号（登录账号）',
  `application_time` datetime(0) NOT NULL COMMENT '申请时间（当前时间，精准到分）',
  `expire_time` datetime(0) NOT NULL COMMENT '到期时间 (试用下是默认七天后到期，状态改成停用)',
  `province` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '地址(省)',
  `area` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '地址(区)',
  `city` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '地址(市)',
  `address` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '详细地址',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态(0试用，1正式，-1禁用)',
  `last_update_time` datetime(0) NOT NULL COMMENT '操作时间',
  `is_deleted` int(11) NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`enterprise_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '企业账号管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_operator_user
-- ----------------------------
DROP TABLE IF EXISTS `t_operator_user`;
CREATE TABLE `t_operator_user`  (
  `uid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `loginname` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `loginpass` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '运营端管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict`  (
  `dict_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '数据id',
  `dict_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '字典名称',
  `dict_data` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '内容',
  `category` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属分类',
  `info` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '信息',
  `last_update_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`dict_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '全局字典表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
