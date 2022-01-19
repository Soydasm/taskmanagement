package com.soydasm.taskmanagement.payload;

import com.soydasm.taskmanagement.model.Bug;
import com.soydasm.taskmanagement.model.Issue;
import com.soydasm.taskmanagement.model.Story;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class IssueResponse extends BaseResponse
{
    private List<IssueDTO> issueDTOList = new ArrayList<>();


    public static List<IssueDTO> convertUserListToUserDTOList(List<Issue> issueList)
    {
        List<IssueDTO> issueDTOList = new ArrayList<>();
        if(issueList != null)
        {
            for (Issue issue : issueList)
            {
                IssueDTO issueDTO = new IssueDTO();
                issueDTO.setTitle(issue.getTitle());
                issueDTO.setDescription(issue.getDescription());
                issueDTO.setDeveloperName(issue.getDeveloper() != null ? issue.getDeveloper().getUsername() : null);
                if(issue instanceof Story)
                {
                    issueDTO.setStatus(((Story) issue).getStatus().getName());
                    issueDTO.setEstimatedPoint(((Story) issue).getEstimatedPoint());
                    issueDTO.setType("story");
                }
                else
                {
                    issueDTO.setStatus(((Bug) issue).getStatus().getName());
                    issueDTO.setType("bug");
                }

                issueDTOList.add(issueDTO);
            }
        }

        return issueDTOList;
    }
}
