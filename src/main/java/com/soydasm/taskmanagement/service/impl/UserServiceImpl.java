package com.soydasm.taskmanagement.service.impl;


import com.soydasm.taskmanagement.controllers.CacheController;
import com.soydasm.taskmanagement.enums.OperationEnum;
import com.soydasm.taskmanagement.model.User;
import com.soydasm.taskmanagement.model.grant.Grant;
import com.soydasm.taskmanagement.model.grant.Role;
import com.soydasm.taskmanagement.model.grant.UserRole;
import com.soydasm.taskmanagement.payload.UserDTO;
import com.soydasm.taskmanagement.payload.UserResponse;
import com.soydasm.taskmanagement.repository.UserRepository;
import com.soydasm.taskmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService
{
    @Autowired
    CacheController cacheController;


    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Override
    public Optional<User> findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public Optional<User> findById(Long id)
    {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public UserResponse saveAndFlush(UserDTO userDTO)
    {
        UserResponse userResponse = new UserResponse();
        userResponse.setHttpStatus(HttpStatus.OK);
        try
        {
            User user = User.convertUserDTOtoUser(userDTO);
            if(userDTO.getUserRoleList() != null)
            {
                Set<UserRole> userRoles =  cacheController.getAllUserRoles().stream()
                        .filter(userRole -> userDTO.getUserRoleList().contains(userRole.getName())).collect(Collectors.toSet());
                user.setUserRoles(userRoles);
            }

            userRepository.saveAndFlush(user);
            userResponse.getUserDTOList().add(userDTO);
        }
        catch (Exception e)
        {
            log.error("Error in saveAndFlush : UUID - " + UUID.randomUUID() + e.getMessage());
            throw e;
        }

        return userResponse;
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable)
    {
        return userRepository.findAll(pageable);
    }


    @Override
    public Boolean isAuthorized(String clientId, String secret, List<String> authorizedRoles, HashMap<String, OperationEnum> authorizedMap)
    {

        Optional<User> userOptional = findByUserName(clientId);
        if(!userOptional.isPresent())
        {
            return false;
        }
        if(!userOptional.get().getPassword().equals(secret)
                || userOptional.get().getUserRoles() == null
                || userOptional.get().getUserRoles().size() == 0)
        {
            return false;
        }

        for(Role role : cacheController.getAllRoles())
        {
            if(role != null && role.getId() != null
                    && authorizedRoles.contains(role.getName())
                    && userOptional.get().getUserRoles().stream().anyMatch(userRole -> userRole.getRoles().contains(role)))
            {
                for(Grant grant : role.getGrants())
                {
                    if(grant != null && grant.getId() != null )
                    {
                        for(String key : authorizedMap.keySet())
                        {
                            if(grant.getEndPoint() != null && grant.getEndPoint().getSuffix().equals(key)
                                    && grant.getOperation().equals(authorizedMap.get(key)))
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public int findAllDeveloperCount()
    {
        return userRepository.findAllDeveloperCount();
    }

}
