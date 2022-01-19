package com.soydasm.taskmanagement.repository;

import com.soydasm.taskmanagement.enums.StoryStatusEnum;
import com.soydasm.taskmanagement.model.Issue;
import com.soydasm.taskmanagement.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long>
{

    @Query("select story from Story story " +
            "where story.status in (:statusList) " +
            "order by story.estimatedPoint desc")
    List<Story> findAllStoriesByStatus(@Param("statusList") List<StoryStatusEnum> statusList);
}
