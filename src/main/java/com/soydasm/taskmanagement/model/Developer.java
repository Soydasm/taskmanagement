package com.soydasm.taskmanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_DEVELOPER")
@Data
@EqualsAndHashCode(callSuper = true, of = "")
@ToString(callSuper = true, of = "")
@Where(clause = "DELETED = '0'")
public class Developer extends User
{

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "developer")
    private Set<Issue> issues = new HashSet<>();
}
