package com.skypyb.dao;

import com.skypyb.entity.Feature;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface FeatureDao {

    @Select("SELECT f.feature_id,f.feature_name FROM feature_to_authority fta,feature f " +
            " WHERE fta.feature_id = f.feature_id AND fta.authority_id = #{authorityId}")
    List<Feature> getByAuthorityId(@Param("authorityId") Long authorityId);


    @Select("SELECT * FROM feature")
    List<Feature> findAll();

    @Delete("DELETE FROM feature WHERE feature_id = #{featureId}")
    void delete(@Param("featureId") Long featureId);

    @Insert(("INSERT INTO feature VALUES(NULL,#{featureName})"))
    void add(@Param("featureName") String featureName);
}
