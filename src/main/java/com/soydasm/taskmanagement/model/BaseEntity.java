package com.soydasm.taskmanagement.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@ToString(of = {"id", "version"})
@EqualsAndHashCode(of = {"id", "version"})
public abstract class BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    public static String DEFAULT_DELETED_VALUE = "0";

    @Id
    private Long id;

    @Version
    private Integer version;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdTime;

    @Column(nullable = false, updatable = false)
    private String createUser;

    @LastModifiedDate
    private Date updatedTime;

    private String updateUser;

    private String deleted = DEFAULT_DELETED_VALUE;

}
