
# spring-framework-henry

spring框架的自己实现，包含spring容器的初始化，bean的创建和获取，aop的实现

## 主要实现注解

- @autowired
- @Scope
- @Component
- @ComponnetScan

  
## bean生命周期中的主要实现内容

- 获取class
- 推断构造方法
- 实例化一个对象
- 属性填充
- 初始化-----一个扩展点 afterPropertiesSet（）之后
- Aop（aop生成代理对象可能） ，使用后置处理器实现的
- bean对象

## 几个关键问题

- 事务
  - 隔离级别
  - 事务传播
  - 失效问题
- 动态代理
- AOP的基本原理和代码
  - 就是通过一个后置处理器来实现的，BeanPostProcessor
- bean的生命周期
