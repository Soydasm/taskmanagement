package com.soydasm.taskmanagement.payload;

import lombok.Data;

import java.util.HashMap;

@Data
public class PlanResults
{
    private int totalElements;

    private HashMap<String, IssueResponse> planningMap = new HashMap<>();
}
