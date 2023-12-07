package spring;

import com.sun.glass.ui.Size;
import henry.BeanDefine;
import spring.ano.*;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HenryApplicationContext {
    private Class configClass;
    private ConcurrentMap<String, Object> instances = new ConcurrentHashMap<>();
    private ConcurrentMap<String, BeanDefine> beans = new ConcurrentHashMap<>();
    //存储beanpostprocessor前后操作的 接口类
    private List<BeanPostProcessor>  beanPostProcessors = new ArrayList<>();

    public HenryApplicationContext(Class configClass) {
        //扫描
        scan(configClass);
        // 实例化单例
        for (Map.Entry<String, BeanDefine> entry : beans.entrySet()) {

            if (entry.getValue().getScope().equals("singleton")) {
                instances.put(entry.getKey(), createBean(entry.getKey(),entry.getValue()));
            }
        }


//        System.out.println("dsags");
//        for (BeanDefine bean :
//                beans) {
//            if (bean.getScope().equals("singleton")){
//                instances.put(bean.getClazz().getName(),createBean(bean));
//            }
//        }
    }

    private Object createBean(String name,BeanDefine beanDefine) {
        Class clazz  = beanDefine.getClazz();
        try {
            Object o = clazz.getDeclaredConstructor().newInstance();
            //实现autowired 注解
            for (Field declareField :
                    clazz.getDeclaredFields()) {
                if (declareField.isAnnotationPresent(Autowired.class)) {
                    //这之前还没有实列对象
                    Object bean = getBean(declareField.getName());
                    declareField.setAccessible(true);
                    declareField.set(o, bean);
                }

            }
            // aware 回调
            if(o instanceof BeanNameAware){
                ((BeanNameAware) o).setBeanName(name);
            }
            // beanpostprocessor 初始化前 里面会有对应类的条件
            for (BeanPostProcessor beanPostProcessor:beanPostProcessors){
                o = beanPostProcessor.postProcessBeforeInitialization(o,name);
            }
            //初始化
            if(o instanceof InitializingBean){
                ((InitializingBean) o).afterPropertiesSet();

            }
            //初始化后
            for (BeanPostProcessor beanPostProcessor:beanPostProcessors){
                o = beanPostProcessor.postProcessAfterInitialization(o,name);
            }
            return o;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void scan(Class configClass) {
        this.configClass = configClass;

        /**
         * 解析配置类
         * 1、获取配置类上的注解
         * 2、根据注解变量获取需要扫描的路径
         * 3、获取下面的类
         * 4、做类头上注解的对应操作
         */
        /**
         * 根据类对象 ，获取关于类的信息、访问和操作对象的属性、方法、构造函数
         */
        ComponentScan componentScan = (ComponentScan) configClass.getAnnotation(ComponentScan.class);//获取该配置类getAnnotation会帮我们生成一个注解子类\
//        configClass.getDeclaredAnnotation(); //和getannotation不同在于，不会向上查找父类的注解
        String path = componentScan.value();//获得路径
/**
 * 启动类加载器（Bootstrap Class Loader）： 是虚拟机的一部分，用来加载Java的核心库，如rt.jar等。 由C++编写，不是一个Java对象，因此在Java代码中无法直接引用。
 * 扩展类加载器（Extension Class Loader）： 负责加载Java的扩展库，位于jre/lib/ext目录下的JAR文件。 由sun.misc.Launcher$ExtClassLoader实现。
 * 应用程序类加载器（Application Class Loader）：也叫系统类加载器，负责加载应用程序的类，即classpath下的类。 由sun.misc.Launcher$AppClassLoader实现。
 * 自定义类加载器： * 开发者可以通过继承ClassLoader类，实现自定义的类加载器。这样的类加载器可以加载用户自定义的类，实现自己的加载规则。
 * classpth 起点   D:\dowr;D:\code\work\mySpring\out\production\springmanual spring.Test
 */
        ClassLoader classLoader = HenryApplicationContext.class.getClassLoader();//通过类加载器获取该路径下的资源
        path = path.replace('.', '/');
        URL resource = classLoader.getResource(path);

        File file = new File(resource.getFile());
        if (file.isDirectory()) {//如果是文件夹，开始扫描每个问题
            File[] files = file.listFiles();
            for (File f : files) {
                String filePath = f.getAbsolutePath();
                if (filePath.endsWith(".class")) {
                    String classPath = filePath.substring(filePath.indexOf("henry"), filePath.indexOf(".class"));
                    try {
                        classPath = classPath.replace('\\', '.');
                        Class<?> subClass = classLoader.loadClass(classPath);
                        if (subClass.isAnnotationPresent(Component.class)) {

                            Component component = subClass.getAnnotation(Component.class);
                            // 获得该注解后，进一步进行操作 service 等等
                            String value = component.value();
                            BeanDefine beanDefine = new BeanDefine();
                            beanDefine.setClazz(subClass);
                            //beanPostProcessor 处理该问题
                            if(BeanPostProcessor.class.isAssignableFrom(subClass)){// 如果该类实现了 我们的处理类 一般只有对应的几个类
                                BeanPostProcessor beanPostProcessor = (BeanPostProcessor) subClass.getDeclaredConstructor().newInstance();
                                beanPostProcessors.add(beanPostProcessor);
                                System.out.println(beanPostProcessors.size());
                            }

                            //scope
                            if (subClass.isAnnotationPresent(Scope.class)) {
                                String scope = subClass.getAnnotation(Scope.class).value();
                                beanDefine.setScope(scope);
                            } else {
                                //设置默认值
                                beanDefine.setScope("singleton");
                            }
                            beans.put(subClass.getDeclaredAnnotation(Component.class).value(), beanDefine);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    //根据类获得类名
    public Object getBean(String name) {
        if (beans.containsKey(name)) {
            if (beans.get(name).getScope().equals("singleton")) {
                return instances.get(name);
            } else {
                return createBean(name,beans.get(name));
            }
        } else {

            System.out.println("不存在这个bean"+name);
            throw new NullPointerException();
        }
    }
}

