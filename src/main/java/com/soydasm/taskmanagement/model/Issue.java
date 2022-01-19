package com.soydasm.taskmanagement.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.soydasm.taskmanagement.payload.IssueDTO;
import com.soydasm.taskmanagement.payload.UserDTO;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "T_ISSUE")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper = true, of = "")
@ToString(callSuper = true, of = "")
@Where(clause = "DELETED = '0'")
public class Issue extends BaseEntity
{
    @NotBlank
    @Size(max = 200, message = "Title length can not be bigger than 200 characters!")
    private String title;

    @NotBlank
    @Size(max = 2000, message = "Description length can not be bigger than 2000 characters!")
    private String description;

    @ManyToOne
    @JoinColumn(name = "DEVELOPER")
    private Developer developer;

    public static Issue convertIssueDTOToIssue(IssueDTO issueDTO)
    {
        Issue issue = new Issue();
        if(issueDTO.getDeleted() != null && !"".equals(issueDTO.getDeleted()))
        {
            issue.setDeleted(issueDTO.getDeleted());
        }
        if(issueDTO.getId() != null)
        {
            issue.setId(issueDTO.getId());
        }
        issue.setVersion(issueDTO.getVersion());
        issue.setCreateUser((issueDTO.getCreateUser() != null && !"".equals(issueDTO.getCreateUser())) ? issueDTO.getCreateUser() :"SYSTEM");
        issue.setCreatedTime(issueDTO.getCreatedTime() != null ? issueDTO.getCreatedTime() : new Date());
        issue.setTitle(issueDTO.getTitle());
        issue.setDescription(issueDTO.getDescription());

        return issue;
    }
}
