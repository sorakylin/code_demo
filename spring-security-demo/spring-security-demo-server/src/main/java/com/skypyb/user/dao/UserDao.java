package com.skypyb.user.dao;

import com.skypyb.user.model.dto.MinimumUserDTO;
import com.skypyb.user.model.po.UserPO;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface UserDao {

    Optional<UserPO> findUserByUserName(@Param("userName") String userName);

    Optional<UserPO> findUserByUserId(@Param("userId") String userId);

    Optional<MinimumUserDTO> findMinimumUser(@Param("userName") String userName);
}
