package com.software.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Wang Hao
 * @date 2022/10/3 0:00
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private MyRealm myRealm;

    /**
     * SecurityManager
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        //1.创建SecurityManager
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //2.创建加密对象并设置相关属性
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //2.1 md5加密
        matcher.setHashAlgorithmName("MD5");
        //2.2 迭代加密次数
        matcher.setHashIterations(3);

        //3.将加密对象存储到Realm中
        myRealm.setCredentialsMatcher(matcher);

        //4.将Realm存入SecurityManager对象
        securityManager.setRealm(myRealm);
        return securityManager;
    }

    /**
     * 拦截器
     *
     * @return
     */
    @Bean
    public DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/doc.html", "anon");
        definition.addPathDefinition("/webjars/**", "anon");
        definition.addPathDefinition("/swagger-resources/**", "anon");
        definition.addPathDefinition("/v2/**", "anon");
        definition.addPathDefinition("/user/login", "anon");
        definition.addPathDefinition("/login", "anon");
        definition.addPathDefinition("/**", "authc");
        return definition;
    }
}
