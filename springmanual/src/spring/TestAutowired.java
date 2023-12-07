package spring;

import henry.AppConfig;
import henry.service.AutowiredService;
import henry.service.UserSerivice;

import java.util.Map;

public class TestAutowired {
    public static void main(String[] args) {
        HenryApplicationContext henryApplicationContext = new HenryApplicationContext(AppConfig.class);
//        AutowiredService autowiredService = (AutowiredService) henryApplicationContext.getBean("autowiredService");
//        autowiredService.test();
        UserSerivice userSerivice = (UserSerivice) henryApplicationContext.getBean("userService");
        userSerivice.test();
    }
}
