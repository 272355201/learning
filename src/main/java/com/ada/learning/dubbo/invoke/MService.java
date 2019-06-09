package com.ada.learning.dubbo.invoke;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class MService {
    private String name;

    public void say(String name){
        log.warn("======say==={}", name);
    }

    public void say(String name,String name2){
        log.warn("======say==={}", name2);
    }
}
