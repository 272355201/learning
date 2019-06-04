package com.ada.learning.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TService implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.warn("TService-loader-{}-{}", this.getClass().getClassLoader(), ClassLoader.getSystemClassLoader());
    }
}
