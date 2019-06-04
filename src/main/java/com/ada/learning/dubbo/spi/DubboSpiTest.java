package com.ada.learning.dubbo.spi;

import com.ada.learning.dubbo.service.Robot;
import com.ada.learning.dubbo.service.RobotDubbo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.compiler.Compiler;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;

import java.util.ServiceLoader;
import java.util.Set;

@Slf4j
public class DubboSpiTest {
    public static void spi(){
        javaSpi();
        dobboSpi();
    }

    private static void javaSpi() {
        ServiceLoader<Robot> load = ServiceLoader.load(Robot.class);
        load.forEach(robot -> robot.sayHello());
    }

    private static void dobboSpi() {
        // 兼容java-spi
        ExtensionLoader<Robot> extensionLoader = ExtensionLoader.getExtensionLoader(Robot.class);
        Set<String> supportedExtensions = extensionLoader.getSupportedExtensions();
        log.info(supportedExtensions.toString());

        // dubbo-spi
        ExtensionLoader<RobotDubbo> dubboExtensionLoader = ExtensionLoader.getExtensionLoader(RobotDubbo.class);
        log.info("supportedExtensions-{}", dubboExtensionLoader.getSupportedExtensions());
        log.info("defaultExtensions-{}", dubboExtensionLoader.getDefaultExtension());
        log.info("Compiler-adapExtensions-{}", ExtensionLoader.getExtensionLoader(Compiler.class).getAdaptiveExtension());
        log.info("Protocal-adapExtensions-{}", ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension());


    }

}
