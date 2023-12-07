package henry.service;

import spring.ano.Component;

@Component("aopService")
public class AopSeriveImp implements AopService{
    @Override
    public void  test(){
        System.out.println("实际业务逻辑");
    }
}
