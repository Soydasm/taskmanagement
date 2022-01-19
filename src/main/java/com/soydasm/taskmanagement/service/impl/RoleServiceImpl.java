package com.soydasm.taskmanagement.service.impl;

import com.soydasm.taskmanagement.model.grant.Role;
import com.soydasm.taskmanagement.repository.RoleRepository;
import com.soydasm.taskmanagement.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService
{
    @Resource(name = "roleRepository")
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAllRoles()
    {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
