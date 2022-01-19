package com.soydasm.taskmanagement.service;

import com.soydasm.taskmanagement.model.Issue;
import com.soydasm.taskmanagement.payload.IssueDTO;
import com.soydasm.taskmanagement.payload.IssueResponse;
import com.soydasm.taskmanagement.payload.PlanResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IssueService
{
    Page<Issue> findAllIssues(Pageable pageable);

    Optional<Issue> findIssueById(Long id);

    IssueResponse saveAndFlush(IssueDTO issueDTO);

    PlanResults findOptimumPlan(Long averageSp);
}
