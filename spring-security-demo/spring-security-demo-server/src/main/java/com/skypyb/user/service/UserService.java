package com.skypyb.user.service;

import com.skypyb.user.dao.UserDao;
import com.skypyb.user.model.dto.MinimumPermissionDTO;
import com.skypyb.user.model.dto.MinimumRoleDTO;
import com.skypyb.user.model.dto.MinimumUserDTO;
import com.skypyb.user.model.dto.RolePermissionPO;
import com.skypyb.user.model.po.UserPO;
import org.mockito.internal.util.collections.Sets;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
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

    /**
     * 查询数据库中所有的权限角色关联
     *
     * @return Map of permissions and roles relation.
     * key: permission id
     * value: role id
     */
    public Map<Long, Set<Long>> findAllPermissionRoleRelation() {
        List<RolePermissionPO> result = dao.findAllRolePermissionRelation();

        Map<Long, Set<Long>> relationMap = new HashMap<>();

        for (RolePermissionPO relation : result) {
            Long permissionId = relation.getPermissionId();//权限
            Long roleId = relation.getRoleId();//角色

            if (relationMap.containsKey(permissionId)) {
                relationMap.get(permissionId).add(roleId);
            } else {
                relationMap.put(permissionId, Sets.newSet(roleId));
            }
        }//权限和角色的映射关系处理完毕

        List<MinimumRoleDTO> allRole = dao.findAllMinimumRoleDTO();

        for (Set<Long> roleIds : relationMap.values()) {

            Set<Long> parentIds = roleIds;

            //查父角色ID，直到找不到父角色
            while (!CollectionUtils.isEmpty(
                    parentIds = parentRoleHandler(parentIds, allRole)
            )) {
                roleIds.addAll(parentIds);
            }
        }

        return relationMap;
    }


    /**
     * 查询数据库中所有的角色权限关联
     *
     * @return Map of roles and permissions relation.
     * key: role id
     * value: permission id
     */
    //TODO 角色继承关系未处理
    public Map<Long, Set<Long>> findAllRolePermissionRelation() {
        List<RolePermissionPO> result = dao.findAllRolePermissionRelation();
        Map<Long, Set<Long>> relationMap = new HashMap<>();

        for (RolePermissionPO relation : result) {
            Long roleId = relation.getRoleId();//角色
            Long permissionId = relation.getPermissionId();//权限

            if (relationMap.containsKey(roleId)) {
                relationMap.get(roleId).add(permissionId);
            } else {
                relationMap.put(roleId, Sets.newSet(permissionId));
            }
        }

        return relationMap;
    }


    /**
     * 父角色处理器,将入参的角色ID集合代表的角色父级查出返回
     *
     * @param roles    要处理的角色ID集合
     * @param allRoles 所有的角色
     */
    private Set<Long> parentRoleHandler(Set<Long> roles, List<MinimumRoleDTO> allRoles) {
        if (CollectionUtils.isEmpty(roles)) return Collections.emptySet();

        //角色ID和角色本体的映射
        Map<Long, MinimumRoleDTO> rolesMap;

        if (CollectionUtils.isEmpty(allRoles)) {
            //所有的角色, 由角色ID对应其角色
            rolesMap = dao.findAllMinimumRoleDTO()
                    .stream().collect(Collectors.toMap(x -> x.getRoleId(), x -> x));
        } else {
            rolesMap = allRoles
                    .stream().collect(Collectors.toMap(x -> x.getRoleId(), x -> x));
        }

        //筛选出这些角色的父级
        return roles.stream()
                .map(rolesMap::get)
                .filter(x -> x != null && x.getParentId() != null)
                .map(un -> un.getParentId())
                .collect(Collectors.toSet());
    }

}
