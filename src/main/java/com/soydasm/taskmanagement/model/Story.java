package com.soydasm.taskmanagement.model;

import com.soydasm.taskmanagement.enums.StoryStatusEnum;
import com.soydasm.taskmanagement.payload.IssueDTO;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "T_STORY")
@Data
@EqualsAndHashCode(callSuper = true, of = "")
@ToString(callSuper = true, of = "")
@Where(clause = "DELETED = '0'")
public class Story extends Issue
{
    private Integer estimatedPoint;

    @Enumerated(EnumType.STRING)
    private StoryStatusEnum status;

    public static IssueDTO convertStoryToIssueDTO(Story story)
    {
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(story.getId());
        issueDTO.setVersion(story.getVersion());
        issueDTO.setDeleted(story.getDeleted());
        issueDTO.setCreatedTime(story.getCreatedTime());
        issueDTO.setCreateUser(story.getCreateUser());
        issueDTO.setTitle(story.getTitle());
        issueDTO.setDescription(story.getDescription());
        issueDTO.setType("story");
        issueDTO.setEstimatedPoint(story.getEstimatedPoint());
        issueDTO.setStatus(story.getStatus().getName());
        issueDTO.setDeveloperName(story.getDeveloper() != null ? story.getDeveloper().getUsername() : null);
        issueDTO.setDeveloperId(story.getDeveloper()!= null ? story.getDeveloper().getId() : null);
        return issueDTO;
    }
}
