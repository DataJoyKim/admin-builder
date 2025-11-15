package com.datajoy.admin_builder.console.dto;

import com.datajoy.admin_builder.function.code.ErrorResolveType;
import com.datajoy.admin_builder.function.code.FunctionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor @Builder
public class WorkflowFunctionResponse {
    private Long id;
    private Long workflowId;
    private String functionName;
    private String displayName;
    private FunctionType functionType;
    private ErrorResolveType errorResolveType;
    private Integer orderNum;
    private Boolean isLogging;
    private String requestMessageId;
    private String responseMessageId;

}
