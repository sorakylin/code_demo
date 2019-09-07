package com.skypyb.user.dao;

import com.skypyb.user.model.dto.MinimumPermissionDTO;
import com.skypyb.user.model.dto.MinimumRoleDTO;
import com.skypyb.user.model.dto.MinimumUserDTO;
import com.skypyb.user.model.dto.RolePermissionPO;
import com.skypyb.user.model.po.UserPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<UserPO> findUserByUserName(@Param("userName") String userName);

    Optional<UserPO> findUserByUserId(@Param("userId") Long userId);

    Optional<MinimumUserDTO> findMinimumUser(@Param("userName") String userName);

    List<MinimumPermissionDTO> findUserMinimumPermission(@Param("userId") Long userId);

    List<MinimumPermissionDTO> findAllMinimumPermission();

    List<MinimumRoleDTO> findAllMinimumRoleDTO();

    List<RolePermissionPO> findAllRolePermissionRelation();

    List<MinimumRoleDTO> findUserMinimumRole(Long userId);
}
