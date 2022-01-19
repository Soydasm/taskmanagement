package com.soydasm.taskmanagement.model.grant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.soydasm.taskmanagement.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicUpdate
@Table(name = "T_ROLE", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Data
@EqualsAndHashCode(callSuper = true, of = "")
@ToString(callSuper = true, of = "")
@Where(clause = "DELETED = '0'")
public class Role extends BaseEntity
{
    @Column(unique=true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "T_ROLE_GRANT",
            joinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "GRANT_ID", referencedColumnName = "ID")})
    private Set<Grant> grants = new HashSet<>();




}
