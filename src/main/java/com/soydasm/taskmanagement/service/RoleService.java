package com.soydasm.taskmanagement.service;

import com.soydasm.taskmanagement.model.grant.Role;

import java.util.List;

public interface RoleService
{
    List<Role> findAllRoles();

    Role findByName(String username);
}
