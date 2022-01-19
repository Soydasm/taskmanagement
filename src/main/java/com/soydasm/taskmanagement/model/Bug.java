package com.soydasm.taskmanagement.model;

import com.soydasm.taskmanagement.enums.BugStatusEnum;
import com.soydasm.taskmanagement.enums.PriorityEnum;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "T_BUG")
@Data
@EqualsAndHashCode(callSuper = true, of = "")
@ToString(callSuper = true, of = "")
@Where(clause = "DELETED = '0'")
public class Bug extends Issue
{
    @Enumerated(EnumType.STRING)
    private PriorityEnum priorityEnum;

    @Enumerated(EnumType.STRING)
    private BugStatusEnum status;
}
