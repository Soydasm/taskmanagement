package com.soydasm.taskmanagement.controllers;

import com.google.common.collect.ImmutableList;
import com.soydasm.taskmanagement.model.grant.Role;
import com.soydasm.taskmanagement.model.grant.UserRole;
import com.soydasm.taskmanagement.service.RoleService;
import com.soydasm.taskmanagement.service.UserRoleService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import java.util.List;

@ApplicationScope
@ManagedBean
public class CacheController
{
    @Resource(name = "roleService")
    private RoleService roleService;

    @Resource(name = "userRoleService")
    private UserRoleService userRoleService;

    private ImmutableList<Role> roleList;

    private ImmutableList<UserRole> userRoleList;


    public List<Role> getAllRoles()
    {
        if(CollectionUtils.isEmpty(roleList))
        {
            roleList = ImmutableList.copyOf(roleService.findAllRoles());
        }
        return roleList;
    }

    public List<UserRole> getAllUserRoles()
    {
        if(CollectionUtils.isEmpty(userRoleList))
        {
            userRoleList = ImmutableList.copyOf(userRoleService.findAllUserRoles());
        }
        return userRoleList;
    }
}
