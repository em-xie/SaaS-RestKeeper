package com.restkeeper.operator.test;

import org.springframework.web.client.RestTemplate;

public class MyThread implements Runnable {


    @Override
    public void run() {

        while (true){
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject("http://localhost:8083/degradeTest", String.class);
            System.out.println(result);
        }



    }
}
