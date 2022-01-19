package com.soydasm.taskmanagement.controllers.rest;

import com.soydasm.taskmanagement.controllers.helper.ControllerHelper;
import com.soydasm.taskmanagement.enums.OperationEnum;
import com.soydasm.taskmanagement.model.Issue;
import com.soydasm.taskmanagement.payload.IssueDTO;
import com.soydasm.taskmanagement.payload.IssueResponse;
import com.soydasm.taskmanagement.payload.PlanResults;
import com.soydasm.taskmanagement.service.IssueService;
import com.soydasm.taskmanagement.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@Api(value = "Issue CRUD Operations ")
@RequestMapping("/v1/issue")
public class IssueController
{

    private static final String CONTROLLER_END = "/v1/issue";

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "userService")
    private UserService userService;


    @GetMapping("/plan/{average_sp}")
    @ApiOperation(value = "Get plan by average_sp")
    public ResponseEntity<PlanResults> getOptimumPlan(@RequestParam("clientId") String clientId,
                                                      @RequestParam("secret") String secret,
                                                      @PathVariable("average_sp") Long averageSp)
    {
        PlanResults planningResults = new PlanResults();
        try
        {

            boolean isAuthorized = userService.isAuthorized(clientId, secret,Arrays.asList("ADMIN", "DEVELOPER", "ISSUE_GET"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.GET));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            planningResults = issueService.findOptimumPlan(averageSp);
            if(planningResults.getTotalElements() == 0)
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        }
        catch (Exception e)
        {
            log.error("Error in findAllIssues : UUID - " + UUID.randomUUID() + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(planningResults);
        }

        return ResponseEntity.status(HttpStatus.OK).body(planningResults);
    }


    @GetMapping
    @ApiOperation(value = "Find all issues by pageable")
    public ResponseEntity<IssueResponse> findAllIssues(@RequestParam("clientId") String clientId,
                                                       @RequestParam("secret") String secret,
                                                       Pageable pageable)
    {
        IssueResponse issueResponse = new IssueResponse();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN", "DEVELOPER", "ISSUE_GET"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.GET));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Page<Issue> issuePage = issueService.findAllIssues(pageable);
            if(issuePage.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            issueResponse.setIssueDTOList(IssueResponse.convertUserListToUserDTOList(issuePage.getContent()));
        }
        catch (Exception e)
        {
            log.error("Error in findAllIssues : UUID - " + UUID.randomUUID() + e.getMessage());
            issueResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            issueResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(issueResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(issueResponse);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "Find issue by id")
    public ResponseEntity<IssueResponse> getIssueById(@RequestParam("clientId") String clientId,
                                                      @RequestParam("secret") String secret,
                                                      @PathVariable("id") Long id)
    {
        IssueResponse issueResponse = new IssueResponse();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN", "DEVELOPER", "ISSUE_GET"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.GET));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Optional<Issue> optionalIssue = issueService.findIssueById(id);
            if(!optionalIssue.isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            issueResponse.getIssueDTOList().add(new IssueDTO(optionalIssue.get()));
        }
        catch (Exception e)
        {
            log.error("Error in getIssueById : UUID - " + UUID.randomUUID() + e.getMessage());
            issueResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            issueResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(issueResponse);
        }

        return ResponseEntity.status(HttpStatus.OK).body(issueResponse);
    }


    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value = "Create a Issue")
    public ResponseEntity<String> createIssue(@RequestParam("clientId") String clientId,
                                              @RequestParam("secret") String secret,
                                              @RequestBody IssueDTO issueDTO)
    {
        IssueResponse issueResponse = new IssueResponse();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.POST));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            issueResponse = issueService.saveAndFlush(issueDTO);
        }
        catch (Exception e)
        {
            log.error("Error in createIssue : UUID - " + UUID.randomUUID() + e.getMessage());
            issueResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            issueResponse.setMessage(e.getMessage());
        }

        if(HttpStatus.OK.equals(issueResponse.getHttpStatus()))
        {
            ResponseEntity.status(HttpStatus.ACCEPTED).body("Issue successfully created!");
        }
        return ResponseEntity.status(issueResponse.getHttpStatus()).body(issueResponse.getMessage());
    }


    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ApiOperation(value = "Update a issue by id")
    public ResponseEntity<String> updateIssue(@RequestParam("clientId") String clientId,
                                              @RequestParam("secret") String secret,
                                              @RequestBody IssueDTO issueDTO)
    {
        IssueResponse issueResponse = new IssueResponse();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.PUT));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            if(issueDTO.getId() == null)
            {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Issue id can not be null!");
            }
            Optional<Issue> optionalIssue = issueService.findIssueById(issueDTO.getId());
            if(!optionalIssue.isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            issueResponse = issueService.saveAndFlush(issueDTO);
        }
        catch (Exception e)
        {
            log.error("Error in updateIssue : UUID - " + UUID.randomUUID() + e.getMessage());
            issueResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            issueResponse.setMessage(e.getMessage());
        }

        if(HttpStatus.OK.equals(issueResponse.getHttpStatus()))
        {
            ResponseEntity.status(HttpStatus.ACCEPTED).body("Issue successfully updated!");
        }
        return ResponseEntity.status(issueResponse.getHttpStatus()).body(issueResponse.getMessage());
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Update an issue by id")
    public ResponseEntity<String> deleteIssue(@RequestParam("clientId") String clientId,
                                              @RequestParam("secret") String secret,
                                              @PathVariable("id") Long id)
    {
        IssueResponse issueResponse = new IssueResponse();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.DELETE));
            if(!isAuthorized)
            {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            if(id == null || "".equals(id))
            {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Id parameter can not be null!");
            }

            Optional<Issue> optionalIssue = issueService.findIssueById(id);
            if(!optionalIssue.isPresent())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            IssueDTO issueDTO = new IssueDTO();
            issueDTO.setId(optionalIssue.get().getId());
            issueDTO.setVersion(optionalIssue.get().getVersion());
            issueDTO.setDeleted("DELETED");
            issueResponse = issueService.saveAndFlush(issueDTO);
        }
        catch (Exception e)
        {
            log.error("Error in deleteIssue : UUID - " + UUID.randomUUID() + e.getMessage());
            issueResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            issueResponse.setMessage(e.getMessage());
        }

        if(HttpStatus.OK.equals(issueResponse.getHttpStatus()))
        {
            ResponseEntity.status(HttpStatus.ACCEPTED).body("Issue successfully deleted!");
        }
        return ResponseEntity.status(issueResponse.getHttpStatus()).body(issueResponse.getMessage());
    }
}
