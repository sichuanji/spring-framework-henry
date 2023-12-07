package annotationProcessor;

import annotation.MyOverride;
import test.Dog;

import java.lang.reflect.Method;

public class OverrideProcessor {

    public static void checkOverrides(Class<?> clazz) {
        //方法反射
        Method[] methods = clazz.getDeclaredMethods();


        for (Method method : methods) {
            //这个方法返回一个布尔值，表示给定的方法是否被指定类型的注解所标注
            if (method.isAnnotationPresent(MyOverride.class)) {
                //返回注解实例，并获取实例属性值
                MyOverride myOverrideAnnotation = method.getAnnotation(MyOverride.class);
                String value = myOverrideAnnotation.value();
                String methodName = method.getName();
                System.out.println("19 value:" +value);
                System.out.println("20 methodName" + methodName);
                try {
                    //获取父类的该方法
                    Method parentMethod = clazz.getSuperclass().getDeclaredMethod(methodName);
                    if (parentMethod.isAnnotationPresent(MyOverride.class)) {
                        MyOverride parentOverride = parentMethod.getAnnotation(MyOverride.class);
                        String parentValue = parentOverride.value();
                        //判断变量是否相同
                        if (!value.equals(parentValue)) {
                            System.out.println("Warning: Method '" + methodName + "' in class '" + clazz.getSimpleName() +
                                    "' is marked with @MyOverride, but the values do not match. Expected: " +
                                    parentValue + ", Actual: " + value);
                        }
                    } else {
                        //该方法父类未声明为重写
                        System.out.println("Warning: Method '" + methodName + "' in class '" + clazz.getSimpleName() +
                                "' is marked with @MyOverride, but the superclass method is not annotated.");
                    }
                } catch (NoSuchMethodException e) {
                    //没有父类方法
                    System.out.println("Error: Method '" + methodName + "' not found in superclass.");
                }
            }
        }
    }

    public static void main(String[] args) {
        checkOverrides(Dog.class);
    }
}