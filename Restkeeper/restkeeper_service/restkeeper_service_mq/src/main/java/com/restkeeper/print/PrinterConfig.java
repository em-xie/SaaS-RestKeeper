package com.restkeeper.print;

import com.restkeeper.constants.SystemCode;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrinterConfig {

    //声明队列
    @Bean(SystemCode.PRINTER_QUEUE_NAME)
    public Queue queue(){
        return new Queue(SystemCode.PRINTER_QUEUE_NAME,true);
    }


    //声明交换机
    @Bean(SystemCode.PRINTER_EXCHANGE_NAME)
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(SystemCode.PRINTER_EXCHANGE_NAME).build();
    }


    //队列绑定交换机
    @Bean
    public Binding bindPrinterQueueToExchange(@Qualifier(SystemCode.PRINTER_QUEUE_NAME)Queue queue,@Qualifier(SystemCode.PRINTER_EXCHANGE_NAME)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(SystemCode.PRINTER_KEY).noargs();
    }
}
