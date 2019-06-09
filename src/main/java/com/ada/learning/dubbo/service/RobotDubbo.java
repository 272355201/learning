package com.ada.learning.dubbo.service;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.rpc.Invocation;

@SPI("B")
public interface RobotDubbo {
    void sayHello();

    @Adaptive
    default void inv(Invocation invocation, URL url){

    }
}