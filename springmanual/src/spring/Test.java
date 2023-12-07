package spring;

import henry.AppConfig;

public class Test {
    public static void main(String[] args) {
        HenryApplicationContext henryApplicationContext = new HenryApplicationContext(AppConfig.class);
        System.out.println(" singleton ");
        System.out.println(henryApplicationContext.getBean("userService"));
        System.out.println(henryApplicationContext.getBean("userService"));
        System.out.println(henryApplicationContext.getBean("userService"));
        System.out.println(" prototype ");
        System.out.println(henryApplicationContext.getBean("scopeService"));
        System.out.println(henryApplicationContext.getBean("scopeService"));
        System.out.println(henryApplicationContext.getBean("scopeService"));


    }
}
