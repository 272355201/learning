package com.ada.learning;

import io.netty.util.concurrent.FastThreadLocal;
import org.junit.Test;

public class NettyTest {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();
    FastThreadLocal<String> fastThreadLocal = new FastThreadLocal<>();

    @Test
    public void testChoose(){
        // 010  1000 0111
        System.out.println(Integer.toBinaryString(5));
        System.out.println(Integer.toBinaryString(-5));
        System.out.println(Integer.toBinaryString(5 & -5));
        System.out.println(Integer.toBinaryString(7));
        System.out.println(Integer.toBinaryString(8));
        System.out.println(Integer.toBinaryString(-8));
        System.out.println(Integer.toBinaryString(8 & -8));
        for (int i = 0; i < 100; i++) {
            System.out.println(i % (6 - 1));
            System.out.println(i & (6 - 1));
        }
    }

    @Test
    public void testThreadLocal(){
        String s = threadLocal.get();
        System.out.println(s);
        threadLocal.set("xxx");

        s = threadLocal.get();
        System.out.println(s);
    }

}
