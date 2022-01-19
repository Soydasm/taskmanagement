package com.soydasm.taskmanagement.repository;

import com.soydasm.taskmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository(value = "userRepository")
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findByUserName(String userName);

    @Query("select count(developer) from Developer developer")
    int findAllDeveloperCount();
}
