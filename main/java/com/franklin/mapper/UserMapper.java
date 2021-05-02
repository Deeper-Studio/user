package com.franklin.mapper;


import com.franklin.pojo.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface UserMapper {
    public User queryUserByName(String name);

    /**
     * 根据用户名查询这个用户的盐值
     * @param name
     * @return
     */
    @Select("select `salt` from user where `name`=#{name}")
    String selectAsaltByName(String name);



}
