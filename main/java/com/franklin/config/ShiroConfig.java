package com.franklin.config;


import com.franklin.filter.PermsFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.servlet.Filter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class ShiroConfig {


    //ShiroFilterBean
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        LinkedHashMap<String,Filter> filters = new LinkedHashMap<>();
        filters.put("perms",new PermsFilter());
        shiroFilterFactoryBean.setFilters(filters);


        /*
            anon:无需认证
            authc:必须认证
            user:必须记住我
            perms:拥有对某个资源的权限
            roles:拥有某个角色权限
         */
        //拦截
        Map<String,String> filterMap = new LinkedHashMap<>();

        /*
             filterChainDefinitionMap.put("/user/add","authc");
            filterChainDefinitionMap.put("/user/update","authc");
         */
        //授权
        filterMap.put("/user/admin","perms[admin|organization]");
        filterMap.put("/user/organization","perms[admin,organization,]");
        filterMap.put("/user/organization","perms[staff]");
        filterMap.put("/user/staff","perms[admin|organization|staff]");
        filterMap.put("/user/*","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        //未认证将跳转至登录界面
         shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //登出将回到首页
        filterMap.put("/logout","logout");
        //没有权限将跳转至特定界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");

        return  shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }



    //DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    //创建realm对象
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
