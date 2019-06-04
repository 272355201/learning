package com.ada.learning.dubbo.service;

import lombok.extern.slf4j.Slf4j;

/**
 * 大黄蜂
 */
@Slf4j
public class Bumblebee implements Robot, RobotDubbo {

    @Override
    public void sayHello() {
        log.info("Hello, I am Bumblebee.");
    }
}