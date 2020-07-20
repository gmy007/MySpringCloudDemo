package com.atguigu.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 此Ribbon规则配置类不能放在主启动类：OrderMain80下
 *  因为@SpringbootApplication注解带有@ComponentScan
 *  er自定义配置类不能放在@ComponentScan注解扫描的包下
 *  否则这个配置类会被所有的Riboon客户端共享，不能实现特殊化定制的功能
 */
@Configuration
public class MyselfRule {
    @Bean
    public IRule getRule(){
        return new RandomRule();
    }
}
