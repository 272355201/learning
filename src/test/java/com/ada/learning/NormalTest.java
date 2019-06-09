package com.ada.learning;

import com.ada.learning.dubbo.invoke.InvokeTest;
import com.ada.learning.dubbo.spi.DubboSpiTest;
import com.ada.learning.guava.CollectionsTest;
import com.ada.learning.jvm.Loader;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class NormalTest {
    @Test
    public void test(){
        CollectionsTest.mapAndList();
        CollectionsTest.hash();
    }

    @Test
    public void test2(){
        Loader.test();
    }

    @Test
    public void test3(){
        DubboSpiTest.spi();
    }

    @Test
    public void test4(){
        InvokeTest.wrapper();
    }
}
