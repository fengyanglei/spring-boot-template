package com.fyl.boot.web.controller.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * mq接收
 */
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(value = "${spring.profiles.active}", durable = "true"),
                exchange = @Exchange(value = "${rabbitmq.exchange}", type = ExchangeTypes.FANOUT, durable = "true"))
)
@Component
public class MqReceiveController {

    @RabbitHandler
    public void receiveMsg(String data) {
        System.out.println(LocalTime.now() + " receive start : " + data);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(LocalTime.now() + " receive end : " + data);
    }

}
