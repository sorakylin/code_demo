package com.skypyb.sc.dao;

import com.skypyb.sc.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserDao {


    @Select("SELECT * FROM user WHERE id=#{id}")
    User findById(@Param("id") Long id);
}
