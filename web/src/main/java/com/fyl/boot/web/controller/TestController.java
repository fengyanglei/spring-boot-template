package com.fyl.boot.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 测试服务连接
 */
@RestController
@RequestMapping("test")
public class TestController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("")
    public Object test() {
        logger.info("test");
        return new Date();
    }

}
