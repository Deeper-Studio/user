package com.franklin.controller;


import com.franklin.pojo.User;
import com.franklin.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyController {

    @Autowired
    @Qualifier("userServiceImpl")
    public static UserService service;

//shiro
    @RequestMapping({"/index","/"})
    public String toIndex(Model model){
        model.addAttribute("msg","Hello,Please login first");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "/user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "/user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "/login";
    }

    @RequestMapping("/user/admin")
    public String admin(){
        return "/user/admin";
    }

    @RequestMapping("/user/organization")
    public String organization(){
        return "/user/organization";
    }

    @RequestMapping("/user/staff")
    public String staff(){
        return "/user/staff";
    }

    @RequestMapping("/user/student")
    public String student(){
        return "/user/student";
    }

    @RequestMapping("/user/guest")
    public String guest(){
        return "/user/guest";
    }


    /**
     * 用户注册时加密用户的密码
     * 输入密码明文 返回密文与盐值
     * @param password
     * @return 第一个是密文  第二个是盐值
     */
    public static String[] encryptPassword(String password)
    {
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex(); //生成盐值
        String ciphertext = new Md5Hash(password,salt,3).toString(); //生成的密文

        String[] strings = new String[]{salt, ciphertext};

        return strings;
    }

    /**
     * 获得本次输入的密码的密文
     * @param password
     * @param salt
     * @return
     */
    public static String getInputPasswordCiph(String password, String salt)
    {
        if(salt == null)
        {
            password = "";
        }

        String ciphertext = new Md5Hash(password,salt,3).toString(); //生成的密文

        return ciphertext;
    }


    @RequestMapping("/login")
    public static String login(User user,String username, String password, Model model ){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登陆数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        user.setPassword(MyController.getInputPasswordCiph(user.getPassword(), service.selectAsaltByName(user.getName())));

        try{
            subject.login(token);//验证成功则返回首页
            return "index";
        }catch (UnknownAccountException e){ //用户名错误
            model.addAttribute("msg","用户名或密码错误");
            return "login";
        }catch (IncorrectCredentialsException e){//密码错误
            model.addAttribute("msg","用户名或密码错误");
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(){
        return "redirect:/toLogin";
    }

    @RequestMapping("/noauth")
    @ResponseBody
    public String unauthorized(){
        return "没有相应权限";
    }


}


