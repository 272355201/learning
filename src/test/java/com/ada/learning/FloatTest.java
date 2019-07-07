package com.ada.learning;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class FloatTest {

    @Test
    @SneakyThrows
    public void test01(){
        Runnable runnable = () -> System.out.println("122");
        System.out.println(runnable.getClass());

        Thread.sleep(100000);
        float sum = 0.0f;
        for (int i = 0; i < 20000000; i++) {
            float x = 1.0f;
            sum += x;
        }
        log.info("sum is {}", sum);

    }

    @Test
    public void test02(){
        float sum = 0.0f;
        float c = 0.0f;
        for (int i = 0; i < 20000000; i++) {
            float x = 1.0f;
            float y = x - c;
            float t = sum + y;
            c = (t-sum)-y;
            sum = t;
        }
        log.info("sum is {}", sum);

    }
}
