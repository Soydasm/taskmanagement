package com.soydasm.taskmanagement.controllers.web;

import com.soydasm.taskmanagement.controllers.helper.ControllerHelper;
import com.soydasm.taskmanagement.enums.OperationEnum;
import com.soydasm.taskmanagement.payload.PlanResults;
import com.soydasm.taskmanagement.service.IssueService;
import com.soydasm.taskmanagement.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController
{
    private static final String CONTROLLER_END = "/admin/v1/plan";

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping({"/plan/{average_sp}", "/plan-overview.html"})
    @ApiOperation(value = "Get plan by average_sp")
    public String getOptimumPlan(@RequestParam("clientId") String clientId,
                                 @RequestParam("secret") String secret,
                                 @PathVariable("average_sp") Long averageSp, Model model)
    {
        PlanResults planningResults = new PlanResults();
        try
        {
            boolean isAuthorized = userService.isAuthorized(clientId, secret, Arrays.asList("ADMIN", "DEVELOPER", "ISSUE_GET"),
                    ControllerHelper.getAuthMap(CONTROLLER_END, OperationEnum.GET));
            if(!isAuthorized)
            {
                return "redirect:/unauthorized.html";
            }

            planningResults = issueService.findOptimumPlan(averageSp);
            model.addAttribute(planningResults);
        }
        catch (Exception e)
        {
            log.error("Error in findAllIssues : UUID - " + UUID.randomUUID() + e.getMessage());
            return "redirect:/internal-error.html";
        }

        return "plan/overview";
    }
}
