package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//我刚刚声明为 Source ，所以运行的时候不检查
public @interface MyOverride {
    //还可声明变量，用于传递参数 想mapperscan 注解就可以传入路径用于指定路径位置
    String value() default "";

}
