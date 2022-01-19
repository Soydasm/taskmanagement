package com.soydasm.taskmanagement.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BaseDTO
{
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private String deleted;

    @JsonIgnore
    private Integer version;

    @JsonProperty(value = "create_user")
    private String createUser;

    @JsonProperty(value = "created_time")
    private Date createdTime;
}
