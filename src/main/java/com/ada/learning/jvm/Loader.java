package com.ada.learning.jvm;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Enumeration;

@Slf4j
public class Loader {
    public static final int i = 1;

    static {
        log.warn("Loader-static-init");
    }

    @SneakyThrows
    public static void test(){
        log.warn("test2-loader-{}", Loader.i);
        log.warn("test2-loader-{}", new Loader().getClass());
        log.warn("test2-loader-{}", new Loader[1].getClass());
        log.warn("test2-loader-{}", new Loader[1][1].getClass());
        log.warn("classloader-{}-{}", String.class, String.class.getClassLoader());
        log.warn("classloader-{}-{}", C.class, C.class.getClassLoader());
        log.warn("system-loader-{}", ClassLoader.getSystemClassLoader());
        log.warn("system-loader-{}", ClassLoader.getSystemClassLoader().getParent());
        log.warn("system-loader-{}", ClassLoader.getSystemClassLoader().getParent().getParent());


        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        log.warn("system-loader-{}", contextClassLoader);
        Enumeration<URL> resources = contextClassLoader.getResources("com.ada.learning.jvm.Loader".replace('.', '/') + ".class");
        while (resources.hasMoreElements()){
            log.warn("resources-{}", resources.nextElement());
        }

        log.warn("int-loader-{}", int.class.getClassLoader());
        log.warn("int-loader-{}", new int[0].getClass().getClassLoader());

        Class<?> aClass = contextClassLoader.loadClass("com.ada.learning.guava.CollectionsTest");
        log.warn("loaded-class-{}", aClass);

    }

    private static class C{

    }
}
