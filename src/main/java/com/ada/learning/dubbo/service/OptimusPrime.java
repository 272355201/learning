package com.ada.learning.dubbo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Adaptive;

/**
 * 擎天柱
 */
@Slf4j
public class OptimusPrime implements Robot, RobotDubbo {
    
    @Override
    public void sayHello() {
        log.info("Hello, I am Optimus Prime.");
    }
}

