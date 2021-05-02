package com.franklin.config;


import com.franklin.pojo.User;
import com.franklin.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;



public class UserRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;
    //JdbcTemplate jdbcTemplate;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo (PrincipalCollection principalCollection) {
        System.out.println("正在执行授权过程");
        //注意不要写成Authen
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录用户对象
        Subject subject = SecurityUtils.getSubject();
        User currUser = (User) subject.getPrincipal();
        //设置当前用户权限，数据库中读取
        info.addStringPermission(currUser.getLevel());
        //if(currUser.getLevel() == null)
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo (AuthenticationToken token) throws AuthenticationException {
        System.out.println("正在执行认证过程");
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        //从数据库种取用户名和密码
        User user = userService.queryUserByName(userToken.getUsername());
        if(user == null){
            return null;
        }

        String userName = token.getPrincipal().toString();
        String passwordInDB = user.getPassword();
        String salt = user.getSalt();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, passwordInDB, ByteSource.Util.bytes(salt), getName());
        return authenticationInfo;


    }
}
