package henry.service;

import spring.ano.*;

@Component("userService")
public class UserSerivice implements BeanNameAware, InitializingBean {
    @Autowired
    private OrderService orderService;
    private String beanName;

    public  void test(){
        System.out.println(orderService);
        System.out.println(beanName);
    }

    @Override
    public void setBeanName(String name) {
        //设置bean的名字，spring调用该方法来实现设置bean的名字
        this.beanName = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化操作");
    }
}
