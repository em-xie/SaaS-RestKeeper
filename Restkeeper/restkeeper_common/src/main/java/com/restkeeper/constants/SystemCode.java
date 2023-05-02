package com.restkeeper.constants;

/**
 * 系统常量类
 */
public class SystemCode {

    // 口味描述
    public final static String DISH_FLAVOR ="flavor";

    //运营端账号下发队列
    public final static String SMS_ACCOUNT_QUEUE="account_queue";

    //1 集团类型  2 门店类型
    public  final  static String  USER_TYPE_SHOP="1"; //集团用户类型
    public  final  static String  USER_TYPE_STORE_MANAGER="2"; //门店管理员类型
    public  final  static String  USER_TYPE_STAFF="3";  //普通员工

    //禁用
    public  final  static int  FORBIDDEN=0;
    //开启
    public  final  static int  ENABLED=1;

    public  final  static int  DISH_TYPE_MORMAL=1; //普通菜品
    public  final  static int  DISH_TYPE_SETMEAL=2; //套餐

    // 挂账类型：1 公司 2 个人
    public final static int CREDIT_TYPE_USER = 2;
    public final static int CREDIT_TYPE_COMPANY = 1;

    public  final  static int  TABLE_STATUS_FREE=0; // 0空闲
    public  final  static int  TABLE_STATUS_LOCKED=1; // 1 锁定
    public  final  static int  TABLE_STATUS_OPENED=2; // 2 已开桌


    public  final  static  String DICTIONARY_REMARK="remark"; //字典表备注类型

    // 订单状态 0：未付 1：已付
    public final static int ORDER_STATUS_NOTPAY = 0;
    public final static int ORDER_STATUS_PAYED = 1;

    // 订单来源 0 门店 1 app
    public final static int ORDER_SOURCE_STORE = 0;
    public final static int ORDER_SOURCE_APP = 1;


    // 打印类型 0:后厨制作菜单 1:后厨转菜单 2:后厨转台单 3:后厨退菜单 4:前台预结单 5:结账单 6:客单
    public final static int PRINT_MADE_MENU = 0;
    public final static int PRINT_CHANGE_MENU = 1;
    public final static int PRINT_CHANGE_TABLE = 2;
    public final static int PRINT_RETURN_DISH = 3;
    public final static int PRINT_BEFOREHAND = 4;
    public final static int PRINT_BILL = 5;
    public final static int PRINT_CUSTOMER = 6;

    public static final String PRINTER_QUEUE_NAME = "print_app_queue";

    public static final String PRINTER_EXCHANGE_NAME = "print_app_exchange";

    public static final String PRINTER_KEY="print_key";
}
