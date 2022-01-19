package com.soydasm.taskmanagement.service;

import com.soydasm.taskmanagement.enums.OperationEnum;
import com.soydasm.taskmanagement.model.User;
import com.soydasm.taskmanagement.payload.UserDTO;
import com.soydasm.taskmanagement.payload.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserService
{
    Optional<User> findByUserName(String userName);

    Optional<User> findById(Long id);

    UserResponse saveAndFlush(UserDTO userDTO);

    Page<User> findAllUsers(Pageable pageable);

    Boolean isAuthorized(String clientId, String secret, List<String> authorizedRoles, HashMap<String, OperationEnum> authorizedMap);

    int findAllDeveloperCount();
}
