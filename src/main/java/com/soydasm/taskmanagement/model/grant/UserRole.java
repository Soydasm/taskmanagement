package com.soydasm.taskmanagement.model.grant;


import com.soydasm.taskmanagement.model.BaseEntity;

import com.soydasm.taskmanagement.model.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "T_USER_ROLE", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Data
@EqualsAndHashCode(callSuper = true, of = "")
@ToString(callSuper = true, of = "")
@Where(clause = "DELETED = '0'")
public class UserRole extends BaseEntity
{
    @Column(unique=true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "T_USER_ROLE_ROLE",
            joinColumns = {@JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private Set<Role> roles = new HashSet<>();


}

