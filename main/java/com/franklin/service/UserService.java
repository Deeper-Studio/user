package com.franklin.service;

import com.franklin.pojo.User;

import java.util.List;

public interface UserService {
    public User queryUserByName(String name);

    /**
     * 用户注册
     * @param user
     */
    void userRegister(User user);

    /**
     * 根据用户名查询这个用户的盐值
     * @param name
     * @return
     */
    String selectAsaltByName(String name);
}
