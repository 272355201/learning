package com.ada.learning.dubbo.service;

import org.apache.dubbo.common.extension.SPI;

@SPI("B")
public interface RobotDubbo {
    void sayHello();
}