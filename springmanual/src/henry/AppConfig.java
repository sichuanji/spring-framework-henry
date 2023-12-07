package henry;

import spring.ano.Component;
import spring.ano.ComponentScan;

//主要注解下面的注解， 也可通过配置文件来配置
@ComponentScan("henry.service")
public class AppConfig {
}
