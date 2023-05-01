/*
 Navicat Premium Data Transfer

 Source Server         : restkeeper-128
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 192.168.200.128:3306
 Source Schema         : restkeeper_order

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 03/08/2020 17:20:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_his_order
-- ----------------------------
DROP TABLE IF EXISTS `t_his_order`;
CREATE TABLE `t_his_order`  (
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `order_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流水号',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属商户',
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属门店',
  `table_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '桌台id',
  `pay_status` int(11) NULL DEFAULT 0 COMMENT '支付状态 0 未付 1 已付  ',
  `pay_type` int(11) NULL DEFAULT NULL COMMENT '付款方式 0 免单 1 现金 2 微信 3 支付宝  4 银行卡 5挂账',
  `total_amount` int(11) NULL DEFAULT NULL COMMENT '应收金额',
  `small_amount` int(11) NULL DEFAULT NULL COMMENT '抹零金额',
  `present_amount` int(11) NULL DEFAULT NULL COMMENT '赠菜金额',
  `pay_amount` int(11) NULL DEFAULT NULL COMMENT '付款金额',
  `free_amount` int(11) NULL DEFAULT NULL COMMENT '免单金额',
  `person_numbers` int(11) NULL DEFAULT NULL COMMENT '就餐人数',
  `order_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '整单备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `operator_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作人名称',
  `buy_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '够买人id',
  `order_source` int(11) NOT NULL DEFAULT 0 COMMENT '订单来源 0=堂点 1=小程序',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '历史订单主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_his_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_his_order_detail`;
CREATE TABLE `t_his_order_detail`  (
  `detail_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单id',
  `order_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单流水号',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属商户',
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属门店',
  `table_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属桌台',
  `detail_status` int(11) NOT NULL DEFAULT 1 COMMENT '状态 1正常 2 赠菜 3 退菜  4 加菜',
  `flavor_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '口味备注',
  `present_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '赠菜备注',
  `return_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '退菜备注',
  `add_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '加菜备注',
  `dish_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜品id',
  `dish_type` int(11) NOT NULL COMMENT '类型 1 菜品 2 套餐',
  `dish_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `dish_price` int(11) NOT NULL COMMENT '单价',
  `dish_number` int(11) NOT NULL COMMENT '份数',
  `dish_amount` int(11) NULL DEFAULT NULL COMMENT '金额',
  `dish_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注信息',
  `dish_category_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜品分类名称',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`detail_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '历史订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `order_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流水号',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属商户',
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属门店',
  `table_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '桌台id',
  `pay_status` int(11) NULL DEFAULT 0 COMMENT '支付状态 0 未付 1 已付  2 支付中',
  `pay_type` int(11) NULL DEFAULT NULL COMMENT '付款方式 0 免单 1 现金 2 微信 3 支付宝  4 银行卡 5挂账',
  `total_amount` int(11) NULL DEFAULT NULL COMMENT '应收金额',
  `small_amount` int(11) NULL DEFAULT 0 COMMENT '抹零金额',
  `present_amount` int(11) NULL DEFAULT 0 COMMENT '璧犺彍閲戦',
  `pay_amount` int(11) NULL DEFAULT 0 COMMENT '付款金额',
  `free_amount` int(11) NULL DEFAULT 0 COMMENT '免单金额',
  `person_numbers` int(11) NULL DEFAULT NULL COMMENT '就餐人数',
  `order_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '鏁村崟澶囨敞',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `operator_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人名称',
  `buy_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '够买人id',
  `order_source` int(11) NOT NULL DEFAULT 0 COMMENT '订单来源 0=堂点 1=小程序',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail`;
CREATE TABLE `t_order_detail`  (
  `detail_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单id',
  `order_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单流水号',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属商户',
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属门店',
  `table_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属桌台',
  `detail_status` int(11) NOT NULL DEFAULT 1 COMMENT '状态 1正常 2 赠菜 3 退菜  4 加菜',
  `flavor_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '口味备注',
  `present_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '赠菜备注',
  `return_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '閫€鑿滃娉?',
  `add_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '加菜备注',
  `dish_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜品id',
  `dish_type` int(11) NOT NULL COMMENT '类型 1 菜品 2 套餐',
  `dish_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `dish_price` int(11) NOT NULL COMMENT '单价',
  `dish_number` int(11) NOT NULL COMMENT '份数',
  `dish_amount` int(11) NULL DEFAULT NULL COMMENT '金额',
  `dish_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注信息',
  `dish_category_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜品分类名称',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`detail_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_order_detail_meal
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_meal`;
CREATE TABLE `t_order_detail_meal`  (
  `detail_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单id',
  `order_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单流水号',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属商户',
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属门店',
  `table_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属桌台',
  `detail_status` int(11) NOT NULL DEFAULT 1 COMMENT '状态 1正常 2 赠菜 3 退菜  4 加菜',
  `flavor_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '口味备注',
  `present_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '赠菜备注',
  `return_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '退菜备注',
  `add_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '加菜备注',
  `dish_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜品id',
  `dish_type` int(11) NOT NULL COMMENT '类型 1 菜品 2 套餐',
  `dish_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `dish_price` int(11) NOT NULL COMMENT '单价',
  `dish_number` int(11) NOT NULL COMMENT '份数',
  `dish_amount` int(11) NULL DEFAULT NULL COMMENT '金额',
  `dish_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注信息',
  `dish_category_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜品分类名称',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`detail_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '套餐订单详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_report_dish
-- ----------------------------
DROP TABLE IF EXISTS `t_report_dish`;
CREATE TABLE `t_report_dish`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pay_date` date NULL DEFAULT NULL COMMENT '支付日期',
  `category` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称',
  `dish_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜品名称',
  `dish_number` int(11) NULL DEFAULT NULL COMMENT '销售量',
  `dish_money` int(11) NULL DEFAULT NULL COMMENT '销售额',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '集团id',
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺id',
  `last_update_time` datetime(0) NULL DEFAULT NULL,
  `is_deleted` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1286604902990155777 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_report_pay
-- ----------------------------
DROP TABLE IF EXISTS `t_report_pay`;
CREATE TABLE `t_report_pay`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pay_date` date NULL DEFAULT NULL COMMENT '日期',
  `pay_type` int(11) NULL DEFAULT NULL COMMENT '收款方式',
  `total_amount` int(11) NULL DEFAULT NULL COMMENT '应收金额',
  `present_amount` int(11) NULL DEFAULT NULL COMMENT '赠送金额',
  `small_amount` int(11) NULL DEFAULT NULL COMMENT '抹零金额',
  `free_amount` int(11) NULL DEFAULT 0 COMMENT '免单金额',
  `pay_amount` int(11) NULL DEFAULT NULL COMMENT '实收金额',
  `person_numbers` int(11) NULL DEFAULT NULL COMMENT '就餐人数',
  `pay_count` int(11) NULL DEFAULT 0 COMMENT '交易单数',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '集团ID',
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺ID',
  `last_update_time` datetime(0) NULL DEFAULT NULL,
  `is_deleted` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1286604902734303233 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_report_time
-- ----------------------------
DROP TABLE IF EXISTS `t_report_time`;
CREATE TABLE `t_report_time`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pay_date` date NULL DEFAULT NULL COMMENT '日期',
  `pay_time` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '时间段',
  `total_count` int(11) NULL DEFAULT NULL COMMENT '单数',
  `total_amount` int(11) NULL DEFAULT NULL COMMENT '销售额',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '集团id',
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1286604902516199425 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_reverse_order
-- ----------------------------
DROP TABLE IF EXISTS `t_reverse_order`;
CREATE TABLE `t_reverse_order`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关联订单号',
  `order_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流水号',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属商户',
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属门店',
  `table_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '桌台id',
  `status` int(11) NULL DEFAULT 1 COMMENT ' 0 失败 1 成功  ',
  `amount` int(11) NULL DEFAULT NULL COMMENT '反结账金额',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `operator_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作人名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '反结账主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 215 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for v_order
-- ----------------------------
DROP VIEW IF EXISTS `v_order`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_order` AS select `t_order`.`order_id` AS `order_id`,`t_order`.`order_number` AS `order_number`,`t_order`.`shop_id` AS `shop_id`,`t_order`.`store_id` AS `store_id`,`t_order`.`table_id` AS `table_id`,`t_order`.`pay_status` AS `pay_status`,`t_order`.`pay_type` AS `pay_type`,`t_order`.`total_amount` AS `total_amount`,`t_order`.`small_amount` AS `small_amount`,`t_order`.`present_amount` AS `present_amount`,`t_order`.`pay_amount` AS `pay_amount`,`t_order`.`free_amount` AS `free_amount`,`t_order`.`person_numbers` AS `person_numbers`,`t_order`.`order_remark` AS `order_remark`,`t_order`.`create_time` AS `create_time`,`t_order`.`last_update_time` AS `last_update_time`,`t_order`.`is_deleted` AS `is_deleted`,`t_order`.`operator_name` AS `operator_name`,`t_order`.`buy_id` AS `buy_id`,`t_order`.`order_source` AS `order_source` from `t_order` union all select `t_his_order`.`order_id` AS `order_id`,`t_his_order`.`order_number` AS `order_number`,`t_his_order`.`shop_id` AS `shop_id`,`t_his_order`.`store_id` AS `store_id`,`t_his_order`.`table_id` AS `table_id`,`t_his_order`.`pay_status` AS `pay_status`,`t_his_order`.`pay_type` AS `pay_type`,`t_his_order`.`total_amount` AS `total_amount`,`t_his_order`.`small_amount` AS `small_amount`,`t_his_order`.`present_amount` AS `present_amount`,`t_his_order`.`pay_amount` AS `pay_amount`,`t_his_order`.`free_amount` AS `free_amount`,`t_his_order`.`person_numbers` AS `person_numbers`,`t_his_order`.`order_remark` AS `order_remark`,`t_his_order`.`create_time` AS `create_time`,`t_his_order`.`last_update_time` AS `last_update_time`,`t_his_order`.`is_deleted` AS `is_deleted`,`t_his_order`.`operator_name` AS `operator_name`,`t_his_order`.`buy_id` AS `buy_id`,`t_his_order`.`order_source` AS `order_source` from `t_his_order`;

-- ----------------------------
-- View structure for v_order_detail
-- ----------------------------
DROP VIEW IF EXISTS `v_order_detail`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_order_detail` AS select `t_order_detail`.`detail_id` AS `detail_id`,`t_order_detail`.`order_id` AS `order_id`,`t_order_detail`.`order_number` AS `order_number`,`t_order_detail`.`shop_id` AS `shop_id`,`t_order_detail`.`store_id` AS `store_id`,`t_order_detail`.`table_id` AS `table_id`,`t_order_detail`.`detail_status` AS `detail_status`,`t_order_detail`.`flavor_remark` AS `flavor_remark`,`t_order_detail`.`present_remark` AS `present_remark`,`t_order_detail`.`return_remark` AS `return_remark`,`t_order_detail`.`add_remark` AS `add_remark`,`t_order_detail`.`dish_id` AS `dish_id`,`t_order_detail`.`dish_type` AS `dish_type`,`t_order_detail`.`dish_name` AS `dish_name`,`t_order_detail`.`dish_price` AS `dish_price`,`t_order_detail`.`dish_number` AS `dish_number`,`t_order_detail`.`dish_amount` AS `dish_amount`,`t_order_detail`.`dish_remark` AS `dish_remark`,`t_order_detail`.`dish_category_name` AS `dish_category_name`,`t_order_detail`.`last_update_time` AS `last_update_time`,`t_order_detail`.`is_deleted` AS `is_deleted` from `t_order_detail` union all select `t_his_order_detail`.`detail_id` AS `detail_id`,`t_his_order_detail`.`order_id` AS `order_id`,`t_his_order_detail`.`order_number` AS `order_number`,`t_his_order_detail`.`shop_id` AS `shop_id`,`t_his_order_detail`.`store_id` AS `store_id`,`t_his_order_detail`.`table_id` AS `table_id`,`t_his_order_detail`.`detail_status` AS `detail_status`,`t_his_order_detail`.`flavor_remark` AS `flavor_remark`,`t_his_order_detail`.`present_remark` AS `present_remark`,`t_his_order_detail`.`return_remark` AS `return_remark`,`t_his_order_detail`.`add_remark` AS `add_remark`,`t_his_order_detail`.`dish_id` AS `dish_id`,`t_his_order_detail`.`dish_type` AS `dish_type`,`t_his_order_detail`.`dish_name` AS `dish_name`,`t_his_order_detail`.`dish_price` AS `dish_price`,`t_his_order_detail`.`dish_number` AS `dish_number`,`t_his_order_detail`.`dish_amount` AS `dish_amount`,`t_his_order_detail`.`dish_remark` AS `dish_remark`,`t_his_order_detail`.`dish_category_name` AS `dish_category_name`,`t_his_order_detail`.`last_update_time` AS `last_update_time`,`t_his_order_detail`.`is_deleted` AS `is_deleted` from `t_his_order_detail`;

-- ----------------------------
-- View structure for v_order_detail_all
-- ----------------------------
DROP VIEW IF EXISTS `v_order_detail_all`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_order_detail_all` AS select `t_order_detail`.`detail_id` AS `detail_id`,`t_order_detail`.`order_id` AS `order_id`,`t_order_detail`.`order_number` AS `order_number`,`t_order_detail`.`shop_id` AS `shop_id`,`t_order_detail`.`store_id` AS `store_id`,`t_order_detail`.`table_id` AS `table_id`,`t_order_detail`.`detail_status` AS `detail_status`,`t_order_detail`.`flavor_remark` AS `flavor_remark`,`t_order_detail`.`present_remark` AS `present_remark`,`t_order_detail`.`return_remark` AS `return_remark`,`t_order_detail`.`add_remark` AS `add_remark`,`t_order_detail`.`dish_id` AS `dish_id`,`t_order_detail`.`dish_type` AS `dish_type`,`t_order_detail`.`dish_name` AS `dish_name`,`t_order_detail`.`dish_price` AS `dish_price`,`t_order_detail`.`dish_number` AS `dish_number`,`t_order_detail`.`dish_amount` AS `dish_amount`,`t_order_detail`.`dish_remark` AS `dish_remark`,`t_order_detail`.`dish_category_name` AS `dish_category_name`,`t_order_detail`.`last_update_time` AS `last_update_time`,`t_order_detail`.`is_deleted` AS `is_deleted` from `t_order_detail` where (`t_order_detail`.`dish_type` = 1) union all select `t_order_detail_meal`.`detail_id` AS `detail_id`,`t_order_detail_meal`.`order_id` AS `order_id`,`t_order_detail_meal`.`order_number` AS `order_number`,`t_order_detail_meal`.`shop_id` AS `shop_id`,`t_order_detail_meal`.`store_id` AS `store_id`,`t_order_detail_meal`.`table_id` AS `table_id`,`t_order_detail_meal`.`detail_status` AS `detail_status`,`t_order_detail_meal`.`flavor_remark` AS `flavor_remark`,`t_order_detail_meal`.`present_remark` AS `present_remark`,`t_order_detail_meal`.`return_remark` AS `return_remark`,`t_order_detail_meal`.`add_remark` AS `add_remark`,`t_order_detail_meal`.`dish_id` AS `dish_id`,`t_order_detail_meal`.`dish_type` AS `dish_type`,`t_order_detail_meal`.`dish_name` AS `dish_name`,`t_order_detail_meal`.`dish_price` AS `dish_price`,`t_order_detail_meal`.`dish_number` AS `dish_number`,`t_order_detail_meal`.`dish_amount` AS `dish_amount`,`t_order_detail_meal`.`dish_remark` AS `dish_remark`,`t_order_detail_meal`.`dish_category_name` AS `dish_category_name`,`t_order_detail_meal`.`last_update_time` AS `last_update_time`,`t_order_detail_meal`.`is_deleted` AS `is_deleted` from `t_order_detail_meal` where (`t_order_detail_meal`.`dish_type` = 1);

SET FOREIGN_KEY_CHECKS = 1;
