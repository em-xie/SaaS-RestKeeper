/*
 Navicat Premium Data Transfer

 Source Server         : restkeeper-128
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 192.168.200.128:3306
 Source Schema         : restkeeper_shop

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 03/08/2020 17:21:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_brand
-- ----------------------------
DROP TABLE IF EXISTS `t_brand`;
CREATE TABLE `t_brand`  (
  `brand_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '品牌主键id',
  `brand_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '品牌名称',
  `logo` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '图片地址',
  `category` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属分类',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商户号id',
  `contact` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门店联系人',
  `contact_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人电话',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`brand_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '品牌管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_store
-- ----------------------------
DROP TABLE IF EXISTS `t_store`;
CREATE TABLE `t_store`  (
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店主键id',
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店名称',
  `brand_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '品牌id',
  `province` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '地址(省)',
  `city` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '地址(市)',
  `area` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '地址(区)',
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '详细地址',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态(营业中1，已停用0)',
  `contact` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '门店联系人',
  `contact_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系人电话',
  `store_manager_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '管理员id',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商户id',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `clear_table` int(11) NULL DEFAULT 0 COMMENT '自动清台',
  PRIMARY KEY (`store_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '门店信息账号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_store_manager
-- ----------------------------
DROP TABLE IF EXISTS `t_store_manager`;
CREATE TABLE `t_store_manager`  (
  `store_manager_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `store_manager_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店管理员姓名',
  `store_manager_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店管理员电话',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除 1是删除 0 未删除',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '店长状态（1启用，0停用）',
  PRIMARY KEY (`store_manager_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '门店管理员信息' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
