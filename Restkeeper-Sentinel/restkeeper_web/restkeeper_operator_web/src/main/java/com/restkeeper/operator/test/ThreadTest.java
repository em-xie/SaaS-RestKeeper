package com.restkeeper.operator.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {

    public static void main(String[] args) {

        //线程池
        ExecutorService pool = Executors.newCachedThreadPool();

        for(int i=0;i<100;i++){

            MyThread myThread = new MyThread();
            pool.execute(myThread);
        }
    }
}
