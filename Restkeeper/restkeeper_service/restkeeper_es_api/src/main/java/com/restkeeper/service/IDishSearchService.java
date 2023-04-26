package com.restkeeper.service;

import com.restkeeper.entity.DishEs;
import com.restkeeper.entity.SearchResult;

public interface IDishSearchService {
    SearchResult<DishEs> searchAllByCode(String code, int type, int pageNum, int pageSize);

    SearchResult<DishEs> searchDishByCode(String code, int pageNum, int pageSize);

    SearchResult<DishEs> searchDishByName(String name,int type,int pageNum,int pageSize);
}
