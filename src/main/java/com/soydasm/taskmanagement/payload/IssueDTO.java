package com.soydasm.taskmanagement.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.soydasm.taskmanagement.model.Bug;
import com.soydasm.taskmanagement.model.Issue;
import com.soydasm.taskmanagement.model.Story;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class IssueDTO extends BaseDTO
{

    @NotBlank
    @Size(max = 200, message = "Title length can not be bigger than 200 characters!")
    @JsonProperty(value = "title")
    private String title;

    @NotBlank
    @Size(max = 2000, message = "Description length can not be bigger than 2000 characters!")
    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "developer_name")
    private String developerName;

    @JsonProperty(value = "developer_id")
    private Long developerId;

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "estimated_point")
    private Integer estimatedPoint;

    public IssueDTO(Issue issue)
    {
        setId(issue.getId());
        setVersion(issue.getVersion());
        setCreatedTime(issue.getCreatedTime());
        setCreateUser(issue.getCreateUser());
        if(issue instanceof Story)
        {
            this.status = ((Story) issue).getStatus().getName();
            this.estimatedPoint = ((Story) issue).getEstimatedPoint();
            this.type = "story";
        }
        else
        {
            this.status = ((Bug) issue).getStatus().getName();
            this.type = "bug";
        }

        this.title = issue.getTitle();
        this.description = issue.getDescription();
        this.developerName = issue.getDeveloper() != null ? issue.getDeveloper().getUsername() : null;
        this.developerId = issue.getDeveloper() != null ? issue.getDeveloper().getId() : null;
    }
}
