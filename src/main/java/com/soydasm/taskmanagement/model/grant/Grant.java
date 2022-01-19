package com.soydasm.taskmanagement.model.grant;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.soydasm.taskmanagement.enums.OperationEnum;
import com.soydasm.taskmanagement.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@DynamicUpdate
@Table(name = "T_GRANT")
@Data
@EqualsAndHashCode(callSuper = true, of = "")
@ToString(callSuper = true, of = "")
@Where(clause = "DELETED = '0'")
public class Grant extends BaseEntity
{

    @Enumerated(EnumType.STRING)
    private OperationEnum operation;

    @ManyToOne
    @JoinColumn(name = "endpoint")
    private EndPoint endPoint;
}
