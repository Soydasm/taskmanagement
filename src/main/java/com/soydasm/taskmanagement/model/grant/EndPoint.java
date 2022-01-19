package com.soydasm.taskmanagement.model.grant;

import com.soydasm.taskmanagement.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "T_ENDPOINT")
@Data
@EqualsAndHashCode(callSuper = true, of = "")
@ToString(callSuper = true, of = "")
@Where(clause = "DELETED = '0'")
public class EndPoint extends BaseEntity
{
    private String domain;

    private String port;

    private String suffix;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "endPoint")
    private Set<Grant> grants = new HashSet<>();
}
