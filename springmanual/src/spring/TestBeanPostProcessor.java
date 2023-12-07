package spring;

import henry.AppConfig;
import henry.service.PostProcessorService;

public class TestBeanPostProcessor {
    public static void main(String[] args) {
        HenryApplicationContext henryApplicationContext = new HenryApplicationContext(AppConfig.class);
        PostProcessorService postProcessorService = (PostProcessorService) henryApplicationContext.getBean("postProcessorService");

    }

}
