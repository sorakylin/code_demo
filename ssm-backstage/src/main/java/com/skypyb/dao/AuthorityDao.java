package com.skypyb.dao;

import com.skypyb.entity.Authority;
import com.skypyb.entity.AuthorityToFeature;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Repository
public interface AuthorityDao {

    @Select("SELECT * FROM authority WHERE authority_id = #{id}")
    Authority getById(@Param("id") Long authorityId);


    @Select("SELECT * FROM authority WHERE authority_name = #{name}")
    Authority getByName(@Param("name") String authorityName);


    @Select("SELECT * FROM authority")
    List<Authority> findAll();


    @Select("SELECT * FROM authority WHERE authority_id = #{id}")
    @Results({
            @Result(property = "authorityId", column = "authority_id"),
            @Result(property = "features", column = "authority_id",
                    many = @Many(select = "com.skypyb.dao.FeatureDao.getByAuthorityId"))})
    AuthorityToFeature findAtfById(@Param("id") Long authorityId);

    @Delete("DELETE FROM authority WHERE authority_id = #{authorityId}")
    void delete(@Param("authorityId") Long authorityId);

    @Insert(("INSERT INTO authority VALUES(NULL,#{authorityName})"))
    void add(@Param("authorityName") String authorityName);

    /*********ATF*********/


    @Select("SELECT * FROM authority")
    @Results({@Result(property = "features", column = "authority_id",
            many = @Many(select = "com.skypyb.dao.FeatureDao.getByAuthorityId"))})
    List<AuthorityToFeature> findAtfAll();

    @Delete("DELETE FROM feature_to_authority WHERE authority_id = #{authorityId}")
    void deleteAtfByAid(@Param("authorityId") Long authorityId);

    @Delete("DELETE FROM feature_to_authority WHERE feature_id = #{featureId}")
    void deleteAtfByFid(@Param("featureId") Long featureId);

    @Insert("INSERT INTO feature_to_authority VALUES(null,#{authorityId},#{featureId})")
    void insertAtf(@Param("authorityId") Long authorityId, @Param("featureId") Long featureId);
}
