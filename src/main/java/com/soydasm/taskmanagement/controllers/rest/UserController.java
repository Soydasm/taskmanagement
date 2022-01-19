package com.soydasm.taskmanagement.controllers.rest;

import com.soydasm.taskmanagement.controllers.helper.ControllerHelper;
import com.soydasm.taskmanagement.enums.OperationEnum;
import com.soydasm.taskmanagement.model.User;
import com.soydasm.taskmanagement.payload.UserDTO;
import com.soydasm.taskmanagement.payload.UserResponse;
import com.soydasm.taskmanagement.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@Api(value = "User CRUD Operations ")
@RequestMapping("/v1/user")
public class UserController
{

    private static final String CONTROLLER_END = "/v1/user";

    @Resource(name = "userService")
    private UserService userService;



    @GetMapping
    @ApiOperation(value = "Find all users by pageable")
    public ResponseEntity<UserResponse> findAllUsers(@RequestParam("clientId") String clientId,
                                                     @RequestParam("secret") String secret,
                                                     Pageable pageable)
    {
        UserResponse userResponse = new UserResponse();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN", "DEVELOPER", "ISSUE_GET", "PAGE_READ"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.GET));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Page<User> userPage = userService.findAllUsers(pageable);
            if(userPage.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            userResponse.setUserDTOList(UserResponse.convertUserListToUserDTOList(userPage.getContent()));
        }
        catch (Exception e)
        {
            log.error("Error in findAllUsers : UUID - " + UUID.randomUUID() + e.getMessage());
            userResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            userResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/{userName}")
    @ApiOperation(value = "Find user by userName")
    public ResponseEntity<UserResponse> getUserByUserName(@RequestParam("clientId") String clientId,
                                                          @RequestParam("secret") String secret,
                                                          @PathVariable("userName") String userName)
    {
        UserResponse userResponse = new UserResponse();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN", "DEVELOPER", "ISSUE_GET", "PAGE_READ"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.GET));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Optional<User> optionalUser = userService.findByUserName(userName);
            if(!optionalUser.isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            userResponse.getUserDTOList().add(new UserDTO(optionalUser.get()));
        }
        catch (Exception e)
        {
            log.error("Error in findAllUsers : UUID - " + UUID.randomUUID() + e.getMessage());
            userResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            userResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }


    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value = "Create a User")
    public ResponseEntity<String> createUser(@RequestParam("clientId") String clientId,
                                             @RequestParam("secret") String secret,
                                             @RequestBody UserDTO userDTO)
    {
        UserResponse userResponse = new UserResponse();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.POST));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            userResponse = userService.saveAndFlush(userDTO);
        }
        catch (Exception e)
        {
            log.error("Error in findAllUsers : UUID - " + UUID.randomUUID() + e.getMessage());
            userResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            userResponse.setMessage(e.getMessage());
        }

        if(HttpStatus.OK.equals(userResponse.getHttpStatus()))
        {
            ResponseEntity.status(HttpStatus.ACCEPTED).body("User successfully created!");
        }
        return ResponseEntity.status(userResponse.getHttpStatus()).body(userResponse.getMessage());
    }


    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value = "Update a user by id")
    public ResponseEntity<String> updateUser(@RequestParam("clientId") String clientId,
                                             @RequestParam("secret") String secret,
                                             @RequestBody UserDTO userDTO)
    {
        UserResponse userResponse = new UserResponse();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.POST));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if(userDTO.getId() == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User id can not be null");
            }
            Optional<User> optionalUser = userService.findById(userDTO.getId());
            if(!optionalUser.isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            userResponse = userService.saveAndFlush(userDTO);
        }
        catch (Exception e)
        {
            log.error("Error in findAllUsers : UUID - " + UUID.randomUUID() + e.getMessage());
            userResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            userResponse.setMessage(e.getMessage());
        }

        if(HttpStatus.OK.equals(userResponse.getHttpStatus()))
        {
            ResponseEntity.status(HttpStatus.ACCEPTED).body("User successfully updated!");
        }
        return ResponseEntity.status(userResponse.getHttpStatus()).body(userResponse.getMessage());
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Update a user")
    public ResponseEntity<String> deleteUser(@RequestParam("clientId") String clientId,
                                             @RequestParam("secret") String secret,
                                             @PathVariable("id") Long id)
    {
        UserResponse userResponse = new UserResponse();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.POST));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if(id == null || "".equals(id))
            {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Id parameter can not be null!");
            }

            Optional<User> optionalUser = userService.findById(id);
            if(!optionalUser.isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            UserDTO userDTO = new UserDTO();
            userDTO.setId(optionalUser.get().getId());
            userDTO.setVersion(optionalUser.get().getVersion());
            userDTO.setDeleted("DELETED");
            userResponse = userService.saveAndFlush(userDTO);
        }
        catch (Exception e)
        {
            log.error("Error in findAllUsers : UUID - " + UUID.randomUUID() + e.getMessage());
            userResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            userResponse.setMessage(e.getMessage());
        }

        if(HttpStatus.OK.equals(userResponse.getHttpStatus()))
        {
            ResponseEntity.status(HttpStatus.ACCEPTED).body("User successfully deleted!");
        }
        return ResponseEntity.status(userResponse.getHttpStatus()).body(userResponse.getMessage());
    }


}
