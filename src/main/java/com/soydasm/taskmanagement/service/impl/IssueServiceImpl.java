package com.soydasm.taskmanagement.service.impl;

import com.soydasm.taskmanagement.enums.StoryStatusEnum;
import com.soydasm.taskmanagement.model.Developer;
import com.soydasm.taskmanagement.model.Issue;
import com.soydasm.taskmanagement.model.Story;
import com.soydasm.taskmanagement.model.User;
import com.soydasm.taskmanagement.payload.IssueDTO;
import com.soydasm.taskmanagement.payload.IssueResponse;
import com.soydasm.taskmanagement.payload.PlanResults;
import com.soydasm.taskmanagement.repository.IssueRepository;
import com.soydasm.taskmanagement.service.IssueService;
import com.soydasm.taskmanagement.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service("issueService")
public class IssueServiceImpl implements IssueService
{
    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Resource(name = "userService")
    private UserService userService;

    @Override
    public Page<Issue> findAllIssues(Pageable pageable)
    {
        return issueRepository.findAll(pageable);
    }

    @Override
    public Optional<Issue> findIssueById(Long id)
    {
        return issueRepository.findById(id);
    }

    @Override
    public IssueResponse saveAndFlush(IssueDTO issueDTO)
    {
        IssueResponse issueResponse = new IssueResponse();
        issueResponse.setHttpStatus(HttpStatus.OK);
        try
        {
            Issue issue = Issue.convertIssueDTOToIssue(issueDTO);
            if(issueDTO.getDeveloperId() != null)
            {
                Optional<User> optionalUser = userService.findById(issueDTO.getDeveloperId());
                if(optionalUser.get() instanceof Developer)
                {
                    issue.setDeveloper((Developer)optionalUser.get());
                }
            }

            issueRepository.saveAndFlush(issue);
            issueResponse.getIssueDTOList().add(issueDTO);
        }
        catch (Exception e)
        {
            log.error("Error in saveAndFlush : UUID - " + UUID.randomUUID() + e.getMessage());
            throw e;
        }

        return issueResponse;
    }


    @Override
    public PlanResults findOptimumPlan(Long averageSp)
    {
        List<Story> storyList = issueRepository.findAllStoriesByStatus(Arrays.asList(StoryStatusEnum.NEW, StoryStatusEnum.ESTIMATED));
        int developerCount = userService.findAllDeveloperCount();

        Long totalSp = developerCount * averageSp;
        int cnt = 0;
        int weekCnt = 1;
        int upperCnt = 0;
        int bottomCnt = 1;
        HashMap<Long, Story> plannedStoryMap = new HashMap<>();
        HashMap<String, IssueResponse> planMap = new HashMap<>();
        IssueResponse issueResponse = new IssueResponse();
        while(upperCnt + bottomCnt <= storyList.size())
        {
            if(cnt == totalSp
                    || ((cnt + storyList.get(upperCnt).getEstimatedPoint() >= totalSp)
                    && (plannedStoryMap.get(storyList.get(storyList.size() - bottomCnt).getId()) == null
                    && (cnt + storyList.get(storyList.size() - bottomCnt).getEstimatedPoint()) > totalSp)))
            {
                planMap.put("Week " + weekCnt, issueResponse);
                cnt = 0;
                weekCnt++;
                issueResponse = new IssueResponse();
            }

            if((cnt + storyList.get(upperCnt).getEstimatedPoint() <= totalSp))
            {
                issueResponse.getIssueDTOList().add(Story.convertStoryToIssueDTO(storyList.get(upperCnt)));
                plannedStoryMap.put(storyList.get(upperCnt).getId(), storyList.get(upperCnt));
                cnt += storyList.get(upperCnt).getEstimatedPoint();
                upperCnt++;
            }
            if(cnt < totalSp
                    && ((storyList.size() > 2 && upperCnt < storyList.size() - 1) && (cnt + storyList.get(upperCnt + 1).getEstimatedPoint()) >= totalSp
                    && plannedStoryMap.get(storyList.get(storyList.size() - bottomCnt).getId()) == null
                    && (cnt + storyList.get(storyList.size() - bottomCnt).getEstimatedPoint()) <= totalSp))
            {
                issueResponse.getIssueDTOList().add(Story.convertStoryToIssueDTO(storyList.get(storyList.size() - bottomCnt)));
                plannedStoryMap.put(storyList.get(storyList.size() - bottomCnt).getId(), storyList.get(storyList.size() - bottomCnt));
                cnt += storyList.get(storyList.size() - bottomCnt).getEstimatedPoint();
                bottomCnt++;
            }

        }

        if(planMap.get("Week " + weekCnt) == null)
        {
            planMap.put("Week " + weekCnt, issueResponse);
        }

        PlanResults results = new PlanResults();
        results.setPlanningMap(planMap);
        results.setTotalElements(planMap.keySet().size());

        return results;
    }
}
