package com.soydasm.taskmanagement.payload;

import com.soydasm.taskmanagement.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserResponse extends BaseResponse
{
    private List<UserDTO> userDTOList = new ArrayList<>();


    public static List<UserDTO> convertUserListToUserDTOList(List<User> userList)
    {
        List<UserDTO> userDTOList = new ArrayList<>();
        if(userList != null)
        {
            for (User user : userList)
            {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setCreateUser(user.getCreateUser());
                userDTO.setCreatedTime(user.getCreatedTime());
                userDTO.setVersion(user.getVersion());
                userDTO.setDeleted(user.getDeleted());
                userDTO.setUserName(user.getUsername());
                userDTO.setPassword(user.getPassword());
                userDTO.getUserRoleList().add(user.getUserRoles().stream().map(userRole -> userRole.getName()).collect(Collectors.joining()));
                userDTOList.add(userDTO);
            }
        }

        return userDTOList;
    }
}
