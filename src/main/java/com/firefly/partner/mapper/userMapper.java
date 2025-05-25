package com.firefly.partner.mapper;

import com.firefly.partner.pojo.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface userMapper {

    User getUserByName(String name);

    void insertUsers(@Param("users") List<User> users);

    void deleteUser(@Param("name")String name);

    void updateUser(@Param("user") User user);

}
