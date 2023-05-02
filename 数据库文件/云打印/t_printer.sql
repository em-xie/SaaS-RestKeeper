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

 Date: 02/04/2020 16:25:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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

SET FOREIGN_KEY_CHECKS = 1;
