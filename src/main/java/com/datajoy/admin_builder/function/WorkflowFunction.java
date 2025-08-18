package com.datajoy.admin_builder.function;

import com.datajoy.admin_builder.function.code.ErrorResolveType;
import com.datajoy.admin_builder.function.code.FunctionType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class WorkflowFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "WORKFLOW_ID", nullable = false)
    private Long workflowId;

    @Column(nullable = false, length = 100)
    private String functionName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private FunctionType functionType;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ErrorResolveType errorResolveType;

    @Column
    private Integer orderNum;

    @Column
    private Boolean isLogging;

    @Column(length = 100)
    private String requestMessageId;

    @Column(length = 100)
    private String responseMessageId;

    public void update(
            Long workflowId,
            String functionName,
            FunctionType functionType,
            Integer orderNum,
            Boolean isLogging,
            String requestMessageId,
            String responseMessageId
    ) {
        this.workflowId = workflowId;
        this.functionName = functionName;
        this.functionType = functionType;
        this.orderNum = orderNum;
        this.isLogging = isLogging;
        this.requestMessageId = requestMessageId;
        this.responseMessageId = responseMessageId;
    }
}
