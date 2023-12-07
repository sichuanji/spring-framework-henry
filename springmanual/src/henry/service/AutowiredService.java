package henry.service;

import spring.ano.Autowired;
import spring.ano.Component;

@Component("autowiredService")
public class AutowiredService {

    @Autowired
    private OrderService orderService;
    public  void test(){
        System.out.println(orderService);
    }
}
