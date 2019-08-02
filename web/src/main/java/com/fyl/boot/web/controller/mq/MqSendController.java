package com.fyl.boot.web.controller.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

/**
 * mq发送
 */
@RestController
@RequestMapping("send")
public class MqSendController {

    @Value("${rabbitmq.exchange}")
    private String mqExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping
    public void send(String val) {
        for (int i = 0; i < 10; i++) {
            System.out.println(LocalTime.now() + " send : " + i);
            rabbitTemplate.convertAndSend(mqExchange, "", i + "");
        }

    }

}
