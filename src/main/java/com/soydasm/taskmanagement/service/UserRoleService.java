package com.soydasm.taskmanagement.service;

import com.soydasm.taskmanagement.model.grant.UserRole;

import java.util.List;

public interface UserRoleService
{
    List<UserRole> findAllUserRoles();
}
