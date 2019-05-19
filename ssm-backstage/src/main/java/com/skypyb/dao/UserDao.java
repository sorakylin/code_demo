package com.skypyb.dao;

import com.skypyb.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    @Select("SELECT * FROM user WHERE user_id = #{id}")
    @Results(@Result(property = "authority", column = "authority",
            one = @One(select = "com.skypyb.dao.AuthorityDao.getByName")))
    User getUserById(@Param("id") Long id);

    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    @Results(@Result(property = "authority", column = "authority",
            one = @One(select = "com.skypyb.dao.AuthorityDao.getByName")))
    User getUserByName(@Param("userName") String userName);

    @Select("SELECT * FROM user")
    @Results(@Result(property = "authority", column = "authority",
            one = @One(select = "com.skypyb.dao.AuthorityDao.getByName")))
    List<User> findAll();

    @Select("SELECT * FROM user Limit #{page},#{limit}")
    @Results(@Result(property = "authority", column = "authority",
            one = @One(select = "com.skypyb.dao.AuthorityDao.getByName")))
    List<User> findAllByPage(@Param("page") int page, @Param("limit") int limit);

    @Delete("DELETE FROM user WHERE user_id = #{userId}")
    void delete(@Param("userId") Long userId);

    @Select("SELECT COUNT(user_id) FROM user")
    int count();

    @Insert("INSERT INTO user VALUES(NULL,#{userName},#{password},#{idCard},#{phone},#{sex},#{authority.authorityName})")
    int addUser(User user);

    @Update("UPDATE user SET user_name=#{userName},password=#{password}," +
            "id_card=#{idCard},phone=#{phone},sex=#{sex},authority=#{authority.authorityName} WHERE user_id=#{userId}")
    int editUser(User user);
}
