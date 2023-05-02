package com.restkeeper.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @作者：xie
 * @时间：2023/5/2 14:57
 */
@Component
public class CalculationBusinessLock {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private final String LOCKVALUE="lockvalue";

    //锁标记
    private boolean lock = false;

    //默认锁时长
    private Integer lockTime = 3;

    //定义自旋锁
    public boolean spinLock(String lockKey,RemainderCount remainderCount){

        while (!getLock(lockKey,lockTime)){
            //获取锁失败
            if (remainderCount.getRemainder()<=1) return false;
            //循环频率
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean getLock(String lockKey, Integer lockTime) {
        //设置锁，防止死锁，手动设置失效时长
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockKey, LOCKVALUE);
        if (flag !=null && flag){
            //加锁成功
            redisTemplate.expire(lockKey,lockTime, TimeUnit.SECONDS);
            lock = true;
        }else {
            lock = false;
        }
        return lock;
    }

    //释放锁
    public void unLock(String lockKey){
        redisTemplate.delete(lockKey);
    }

    @FunctionalInterface
    public interface RemainderCount{
        int getRemainder();
    }
}
