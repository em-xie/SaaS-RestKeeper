/*
 Navicat Premium Data Transfer

 Source Server         : restkeeper-128
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : 192.168.200.128:3306
 Source Schema         : restkeeper_store

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 03/08/2020 17:21:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类id',
  `type` int(11) NULL DEFAULT NULL COMMENT '类型1 菜品 2 套餐',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `t_order` int(11) NOT NULL DEFAULT 0 COMMENT '顺序',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店主键id',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品及套餐分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_credit
-- ----------------------------
DROP TABLE IF EXISTS `t_credit`;
CREATE TABLE `t_credit`  (
  `credit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `credit_type` int(11) NULL DEFAULT NULL COMMENT '挂账类型 1 公司 2 个人',
  `company_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '挂账公司名称',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户名称',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '手机号',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '状态 1 开启 0 停用',
  `credit_amount` int(11) NOT NULL DEFAULT 0 COMMENT '挂账金额',
  `total_repayment_amount` int(11) NULL DEFAULT 0 COMMENT '总还款金额',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`credit_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '挂账单位管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_credit_company_user
-- ----------------------------
DROP TABLE IF EXISTS `t_credit_company_user`;
CREATE TABLE `t_credit_company_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `credit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '挂账公司id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名称',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '挂账公司人员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_credit_logs
-- ----------------------------
DROP TABLE IF EXISTS `t_credit_logs`;
CREATE TABLE `t_credit_logs`  (
  `log_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '挂账日志id',
  `order_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单',
  `credit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '挂账关联id',
  `credit_type` int(11) NOT NULL COMMENT '挂账类型 挂账类型  1 公司 2 个人',
  `company_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '挂账公司名称',
  `user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '挂账人名称',
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '挂账人电话',
  `order_amount` int(11) NOT NULL COMMENT '订单金额',
  `received_amount` int(11) NOT NULL COMMENT '应收金额',
  `credit_amount` int(11) NOT NULL COMMENT '挂账金额',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '挂账记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_credit_repayment
-- ----------------------------
DROP TABLE IF EXISTS `t_credit_repayment`;
CREATE TABLE `t_credit_repayment`  (
  `log_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '还款记录id',
  `credit_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '挂账类型 1 个人 2 公司',
  `credit_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '挂账管理id',
  `company_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '挂账公司名称',
  `user_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '还款人',
  `repayment_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '还款方式 1 现金 2 微信 3 支付宝  4 银行卡',
  `repayment_amount` int(11) NULL DEFAULT NULL COMMENT '还款金额',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '还款记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_dish
-- ----------------------------
DROP TABLE IF EXISTS `t_dish`;
CREATE TABLE `t_dish`  (
  `dish_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品Id',
  `dish_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `category_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品分类id',
  `price` int(11) NULL DEFAULT NULL COMMENT '菜品价格',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商品码',
  `image` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '图片',
  `description` varchar(400) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '0 停售 1 起售',
  `last_update_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店主键id',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  PRIMARY KEY (`dish_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_dish_flavor
-- ----------------------------
DROP TABLE IF EXISTS `t_dish_flavor`;
CREATE TABLE `t_dish_flavor`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `dish_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品',
  `flavor_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '口味名称',
  `flavor_value` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '口味数据list',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店主键id',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '菜品口味关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_payment_setting
-- ----------------------------
DROP TABLE IF EXISTS `t_payment_setting`;
CREATE TABLE `t_payment_setting`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `pay_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '支付配置类型 1 支付宝 2 微信',
  `appid` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'appid',
  `public_key` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商户公钥',
  `private_key` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '私钥',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '支付配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_printer
-- ----------------------------
DROP TABLE IF EXISTS `t_printer`;
CREATE TABLE `t_printer`  (
  `printer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `printer_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `machine_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '打印机终端号',
  `hardware_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '打印机硬件版本号',
  `software_version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '软件版本号',
  `state` int(11) NULL DEFAULT 1 COMMENT '打印机状态',
  `area_type` int(11) NOT NULL COMMENT '打印机所在区域类型，1:后厨打印机；2:收银区打印',
  `printer_number` int(11) NOT NULL DEFAULT 1 COMMENT '打印份数',
  `enable_made_menu` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否支持制作菜单',
  `enable_change_menu` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否支持转菜单',
  `enable_change_table` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否支持转台单',
  `enable_return_dish` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否支持退菜打印',
  `enable_beforehand` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否支持前台预结单',
  `enable_bill` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否支持结账单',
  `enable_customer` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否支持打印客单',
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_deleted` int(11) NOT NULL DEFAULT 0,
  `last_update_time` datetime(0) NOT NULL,
  PRIMARY KEY (`printer_id`) USING BTREE,
  UNIQUE INDEX `t_printer_printer_id_uindex`(`printer_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '打印机表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_printer_dish
-- ----------------------------
DROP TABLE IF EXISTS `t_printer_dish`;
CREATE TABLE `t_printer_dish`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dish_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `printer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `store_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `shop_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '打印机菜品关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_remark
-- ----------------------------
DROP TABLE IF EXISTS `t_remark`;
CREATE TABLE `t_remark`  (
  `remark_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '备注id',
  `remark_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '备注名称',
  `remark_value` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注内容',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属商户',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`remark_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '门店备注信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_repayment_logs
-- ----------------------------
DROP TABLE IF EXISTS `t_repayment_logs`;
CREATE TABLE `t_repayment_logs`  (
  `log_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '还款记录id',
  `credit_log_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '挂账记录id',
  `credit_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '挂账类型 1 个人 2 公司',
  `credit_company` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '挂账公司名称',
  `repayment_user` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '还款人',
  `repayment_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '还款方式',
  `repayment_ammount` double NULL DEFAULT NULL COMMENT '还款金额',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '还款记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_sell_calculation
-- ----------------------------
DROP TABLE IF EXISTS `t_sell_calculation`;
CREATE TABLE `t_sell_calculation`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `dish_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品Id',
  `dish_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `dish_type` int(11) NOT NULL COMMENT '1 普通菜品2 套餐',
  `sell_limit_total` int(11) NOT NULL COMMENT '估清总数',
  `remainder` int(11) NOT NULL COMMENT '剩余数',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '沽清' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_setmeal
-- ----------------------------
DROP TABLE IF EXISTS `t_setmeal`;
CREATE TABLE `t_setmeal`  (
  `setmeal_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店主键id',
  `category_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品分类id',
  `setmeal_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐名称',
  `price` int(11) NOT NULL COMMENT '套餐价格',
  `status` int(11) NULL DEFAULT NULL COMMENT '状态 0:停用 1:启用',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '编码',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图片',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店id',
  `last_update_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  PRIMARY KEY (`setmeal_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_setmeal_dish
-- ----------------------------
DROP TABLE IF EXISTS `t_setmeal_dish`;
CREATE TABLE `t_setmeal_dish`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `setmeal_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '套餐id ',
  `dish_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品id',
  `dish_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜品名称 （冗余字段）',
  `dish_copies` int(11) NOT NULL COMMENT '份数',
  `t_order` int(11) NOT NULL COMMENT '排序',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店id',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `last_update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '套餐菜品关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_staff
-- ----------------------------
DROP TABLE IF EXISTS `t_staff`;
CREATE TABLE `t_staff`  (
  `staff_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '员工id',
  `staff_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '员工姓名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '身份证号',
  `status` int(11) NULL DEFAULT 0 COMMENT '状态 0:正常,1-禁用',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店id',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '商户号',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`staff_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '员工信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_staff_rank
-- ----------------------------
DROP TABLE IF EXISTS `t_staff_rank`;
CREATE TABLE `t_staff_rank`  (
  `rank_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '岗位id',
  `rank_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '职级名称',
  `discount_limit` double NULL DEFAULT NULL COMMENT '折扣上线',
  `reduce_limit` double NULL DEFAULT NULL COMMENT '减免金额上线',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店id',
  PRIMARY KEY (`rank_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '员工职级' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_store_manager
-- ----------------------------
DROP TABLE IF EXISTS `t_store_manager`;
CREATE TABLE `t_store_manager`  (
  `store_manager_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `store_manager_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店管理员姓名',
  `store_manager_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '门店管理员电话',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `status` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '1' COMMENT '0 停用 1 正常',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除 1是删除 0 未删除',
  `last_update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`store_manager_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '门店管理员信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_table
-- ----------------------------
DROP TABLE IF EXISTS `t_table`;
CREATE TABLE `t_table`  (
  `table_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '桌台id',
  `area_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '区域id',
  `table_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '桌台名称',
  `table_seat_number` int(11) NOT NULL COMMENT '桌台座位数目',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0空闲，1开桌 2锁桌',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `is_deleted` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '桌台' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_table_area
-- ----------------------------
DROP TABLE IF EXISTS `t_table_area`;
CREATE TABLE `t_table_area`  (
  `area_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '桌台区域id',
  `area_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '区域名称',
  `t_order` int(11) NULL DEFAULT NULL COMMENT '排序',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `last_update_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`area_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '桌台区域' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_table_log
-- ----------------------------
DROP TABLE IF EXISTS `t_table_log`;
CREATE TABLE `t_table_log`  (
  `log_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '开桌记录id',
  `table_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属桌台',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '操作用户',
  `table_status` int(11) NOT NULL COMMENT '桌台操作记录 1 开桌 2 锁桌 ',
  `user_numbers` int(11) NOT NULL COMMENT '用餐人数',
  `is_deleted` int(11) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `store_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属门店',
  `shop_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '所属商户',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `last_update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '桌台记录' ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
