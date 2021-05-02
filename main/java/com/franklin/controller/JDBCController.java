package com.franklin.controller;


import com.franklin.pojo.User;
import com.franklin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class JDBCController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    private UserService service;

    //查询所有信息
    @GetMapping("/userList")
    public List<Map<String,Object>> userList(){
        String sql = "select * from users.user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }

    //查询单个信息
    @GetMapping("/user/{id}")
    public Map<String, Object> user(@PathVariable("id") int id){
        String sql = "select * from users.user where id=" + id;
         return jdbcTemplate.queryForMap(sql);
    }

    //JDBC
    //删除一个用户
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        String sql = "delete * from users.user where id=" + id;
        jdbcTemplate.update(sql);
        return "Add-ok";
    }

    //添加一个用户
    @RequestMapping("/register")
    public String register(User user, Model model,String name, String pwd, String email, String department){

        UUID uuid = UUID.randomUUID();
        String strUUID = uuid.toString().replace("-","");

        String[] saltAndCiphertext = MyController.encryptPassword("123456");


         Object[] objects = new Object[10];
//        objects[0] = strUUID;
//        objects[1] = name;
//        objects[2] = pwd;
//        objects[3] = sex;
//        objects[4] = age;
//        objects[5] = email;
//        objects[6] = "CS";
//        objects[7] = "staff";

        //测试数据
        objects[0] = strUUID;
        objects[1] = "Pengy8";
        objects[2] = "2021cnscc212";
        objects[3] = 1;
        objects[4] = 0;
        objects[5] = "damian@luc";
        objects[6] = "CS";
        objects[7] = "staff";
        objects[8] = saltAndCiphertext[0];
        objects[9] = saltAndCiphertext[1];



        String sql = "insert into users.user(uuid,name,password,sex,age,email,department,level,salt,ciphertext) values" +
                "(?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,objects);
        return "Add-ok";
    }


    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id){
        String sql = "update users.user set name=?,password=?,sex=?,age=?,email=?,department=?,level=?,salt=? where id="+id;
        Object[] objects = new Object[7];
        objects[0] = null;
        objects[1] = null;
        objects[2] = null;
        objects[3] = null;
        objects[4] = null;
        objects[5] = null;
        objects[6] = null;

        jdbcTemplate.update(sql,objects);
        return "update-ok";

    }
}
