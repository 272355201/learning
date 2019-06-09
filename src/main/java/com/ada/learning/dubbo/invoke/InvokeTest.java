package com.ada.learning.dubbo.invoke;

import com.ada.learning.dubbo.service.OptimusPrime;
import com.ada.learning.dubbo.service.RobotDubbo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.bytecode.Wrapper;


@Slf4j
public class InvokeTest {
    @SneakyThrows
    public static void wrapper(){
        log.warn("=======startwrapper=======");
        Wrapper wrapper = Wrapper.getWrapper(MService.class);
        log.info("class-{}", wrapper.getClass());
        wrapper.invokeMethod(new MService(), "say",new Class[]{String.class}, new Object[]{"world"});
    }
}
