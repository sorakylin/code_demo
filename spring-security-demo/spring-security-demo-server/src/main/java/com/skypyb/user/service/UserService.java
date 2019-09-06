package com.skypyb.user.service;

import com.skypyb.user.dao.UserDao;
import com.skypyb.user.model.dto.MinimumPermissionDTO;
import com.skypyb.user.model.dto.MinimumRoleDTO;
import com.skypyb.user.model.dto.MinimumUserDTO;
import com.skypyb.user.model.po.UserPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService {

    @Resource
    private UserDao dao;


    public Optional<UserPO> findUserByUserName(String userName) {
        return dao.findUserByUserName(userName);
    }

    public Optional<UserPO> findUserByUserId(Long userId) {

        return dao.findUserByUserId(userId);
    }

    public Optional<MinimumUserDTO> findMinimumUser(String userName) {
        return dao.findMinimumUser(userName);
    }

    public List<MinimumPermissionDTO> findUserMinimumPermission(Long userId) {
        return dao.findUserMinimumPermission(userId);
    }

    /**
     * 查数据库里所有的权限
     * 一般来说是在系统启动时初始化
     *
     * @return 权限列表
     */
    public List<MinimumPermissionDTO> findAllMinimumPermission() {
        return dao.findAllMinimumPermission();
    }

    /**
     * 查数据库里所有的角色
     * 一般来说是在系统启动时初始化
     *
     * @return 权限列表
     */
    public List<MinimumRoleDTO> findAllMinimumRoleDTO() {
        return dao.findAllMinimumRoleDTO();
    }



}
