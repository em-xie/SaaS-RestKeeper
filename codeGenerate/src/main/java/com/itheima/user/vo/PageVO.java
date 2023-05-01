package com.itheima.user.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * 前端对象转换封装
 * @param <T>
 */
@Data
public class PageVO<T> {

    private  long counts; //前端定义总个数字段

    private  long pagesize; //前端定义每页个数

    private  long pages; //前端定义总页数

    private  long page; //前端定义当前页

    private List<T> items; //前端定义数据记录

    public PageVO(IPage page) {
        //后端对象转换成前端对象
        this.pagesize = page.getSize();
        this.counts = page.getTotal();
        this.page = page.getCurrent();
        this.pages = page.getPages();
        this.items = page.getRecords();
    }
}
