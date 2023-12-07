package henry.service;

import spring.ano.BeanPostProcessor;
import spring.ano.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


@Component("postProcessorService")
public class PostProcessorService implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        //指定的一部分类
        if(beanName.equals("aopService")){
            //生成一个代理对象来处理一些代理逻辑，然后之后具体的方法
            System.out.println("初始化之前 doing");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if(beanName.equals("aopService")){
            //生成一个动态代理对象来处理一些代理逻辑，然后之后具体的方法
            System.out.println("初始化后 doing");
            Object proxyInstance = Proxy.newProxyInstance(BeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("先执行代理逻辑");
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }

        return bean;
    }
}
