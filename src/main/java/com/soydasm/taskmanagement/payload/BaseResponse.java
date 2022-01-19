package com.soydasm.taskmanagement.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class BaseResponse
{
    @JsonIgnore
    private HttpStatus httpStatus = HttpStatus.OK;

    @JsonIgnore
    private String message;
}
