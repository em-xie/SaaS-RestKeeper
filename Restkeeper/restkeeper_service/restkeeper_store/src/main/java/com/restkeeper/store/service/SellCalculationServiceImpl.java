package com.restkeeper.store.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.exception.BussinessException;
import com.restkeeper.lock.CalculationBusinessLock;
import com.restkeeper.store.entity.SellCalculation;
import com.restkeeper.store.mapper.SellCalculationMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service("sellCalculationService")
@Service(version = "1.0.0",protocol = "dubbo")
public class SellCalculationServiceImpl extends ServiceImpl<SellCalculationMapper, SellCalculation> implements ISellCalculationService {
    @Override
    public Integer getRemainderCount(String dishId) {

        QueryWrapper<SellCalculation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SellCalculation::getRemainder).eq(SellCalculation::getDishId,dishId);
        SellCalculation sellCalculation = this.getOne(queryWrapper);
        if (sellCalculation == null){
            //估清表中没数据，代表该菜品为无限制菜品,返回-1
            return -1;
        }
        return sellCalculation.getRemainder();
    }

    @Override
    @Transactional
    public void decrease(String dishId, Integer dishNumber) {

        QueryWrapper<SellCalculation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SellCalculation::getDishId,dishId);
        SellCalculation sellCalculation = this.getOne(queryWrapper);
        if (sellCalculation != null){

            int resultCount = sellCalculation.getRemainder()-dishNumber;
            if (resultCount < 0) resultCount = 0;
            sellCalculation.setRemainder(resultCount);
            this.updateById(sellCalculation);
        }
    }

    @Override
    public void add(String dishId, int i) {
        QueryWrapper<SellCalculation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SellCalculation::getDishId,dishId);
        SellCalculation sellCalculation = this.getOne(queryWrapper);
        sellCalculation.setRemainder(sellCalculation.getRemainder()+i);
        this.updateById(sellCalculation);
    }

    @Autowired
    private CalculationBusinessLock lock;

    @Override
    @Transactional
    public void plusDish(String dishId) {
        //判断沽清
        Integer remainderCount = this.getRemainderCount(dishId);
        if (remainderCount <0){
            return;
        }

        //操作沽清扣减，考虑锁控制
        String key = dishId;
        boolean flag = lock.spinLock(key,()->getRemainderCount(dishId));
        if (!flag){
            throw new BussinessException("商品已被沽清");
        }else {
            //获取到锁
            try {
                QueryWrapper<SellCalculation> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(SellCalculation::getDishId,dishId);
                SellCalculation sellCalculation = this.getOne(queryWrapper);
                sellCalculation.setRemainder(sellCalculation.getRemainder()-1);
                this.updateById(sellCalculation);
            } finally {
                //释放锁
                lock.unLock(key);
            }
        }

    }

    @Override
    @Transactional
    public void reduceDish(String dishId) {

        //判断沽清
        Integer remainderCount = this.getRemainderCount(dishId);
        if (remainderCount <0){
            return;
        }

        QueryWrapper<SellCalculation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SellCalculation::getDishId,dishId);
        SellCalculation sellCalculation = this.getOne(queryWrapper);
        remainderCount = sellCalculation.getRemainder()+1;
        if (remainderCount > sellCalculation.getSellLimitTotal()){
            remainderCount = sellCalculation.getSellLimitTotal();
        }
        sellCalculation.setRemainder(remainderCount);
        this.updateById(sellCalculation);

    }
}
