package com.soydasm.taskmanagement.repository;

import com.soydasm.taskmanagement.model.grant.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>
{

    Role findByName(String name);
}
