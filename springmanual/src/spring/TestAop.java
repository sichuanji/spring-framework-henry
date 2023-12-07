package spring;

import henry.AppConfig;
import henry.service.AopService;
import henry.service.PostProcessorService;

public class TestAop {

    public static void main(String[] args) {
        HenryApplicationContext henryApplicationContext = new HenryApplicationContext(AppConfig.class);
        AopService aopService = (AopService) henryApplicationContext.getBean("aopService");
        aopService.test();
    }
}
