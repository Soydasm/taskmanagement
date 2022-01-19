package com.soydasm.taskmanagement.service.impl;

import com.soydasm.taskmanagement.model.grant.UserRole;
import com.soydasm.taskmanagement.repository.UserRoleRepository;
import com.soydasm.taskmanagement.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService
{
    @Resource(name = "userRoleRepository")
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findAllUserRoles()
    {
        return userRoleRepository.findAll();
    }
}
