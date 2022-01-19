package com.soydasm.taskmanagement.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.soydasm.taskmanagement.model.User;
import jdk.nashorn.internal.runtime.Version;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDTO extends BaseDTO
{
    @NotBlank
    @Size(max = 40, message = "Username length can not be bigger than 40 characters!")
    @JsonProperty(value = "user_name")
    private String userName;

    @NotBlank
    @JsonIgnore
    private String password;

    @NotBlank
    @JsonProperty(value = "user_role_list")
    private List<String> userRoleList = new ArrayList<>();

    public UserDTO(User user)
    {
        setId(user.getId());
        setVersion(user.getVersion());
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.userRoleList.add(user.getUserRoles().stream().map(userRole -> userRole.getName()).collect(Collectors.joining()));
    }
}
