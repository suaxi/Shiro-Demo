package com.software.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

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

        //5.RememberMe
        securityManager.setRememberMeManager(cookieRememberMeManager());

        //6.缓存管理
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    /**
     * cookie管理对象
     *
     * @return
     */
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();

        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //跨域
        //simpleCookie.setDomain("xxx.com");
        simpleCookie.setPath("/");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(30 * 24 * 60 * 60);

        rememberMeManager.setCookie(simpleCookie);
        rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return rememberMeManager;
    }

    /**
     * 缓存管理器
     *
     * @return
     */
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        InputStream is = null;
        try {
            is = ResourceUtils.getInputStreamForPath("classpath:ehcache.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        CacheManager cacheManager = new CacheManager(is);
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    /**
     * 拦截器
     *
     * @return
     */
    @Bean
    public DefaultShiroFilterChainDefinition defaultShiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        //无需认证可访问的资源路径
        definition.addPathDefinition("/doc.html", "anon");
        definition.addPathDefinition("/webjars/**", "anon");
        definition.addPathDefinition("/swagger-resources/**", "anon");
        definition.addPathDefinition("/v2/**", "anon");
        definition.addPathDefinition("/user/doLogin", "anon");
        definition.addPathDefinition("/user/login", "anon");
        //退出
        definition.addPathDefinition("/logout", "logout");
        //需进行认证才能访问的资源路径
        definition.addPathDefinition("/**", "authc");
        //rememberMe
        definition.addPathDefinition("/**", "user");
        return definition;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
