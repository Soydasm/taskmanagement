package com.soydasm.taskmanagement.repository;

import com.soydasm.taskmanagement.model.grant.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "userRoleRepository")
public interface UserRoleRepository extends JpaRepository<UserRole, Long>
{

}
