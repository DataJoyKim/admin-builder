package com.datajoy.admin_builder.function;

import com.datajoy.admin_builder.function.code.FunctionType;
import com.datajoy.admin_builder.function.executor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FunctionFactory {

    private final EntityExecutor entityExecutor;
    private final QueryExecutor queryExecutor;
    private final RestClientExecutor restClientExecutor;
    private final MessageProcessorExecutor messageProcessorExecutor;
    private final NotificationExecutor notificationExecutor;

    public FunctionExecutor instance(FunctionType functionType) {
        return switch (functionType) {
            case ENTITY -> entityExecutor;
            case SQL -> queryExecutor;
            case REST_CLIENT -> restClientExecutor;
            case MESSAGE_PROCESSOR -> messageProcessorExecutor;
            case NOTIFICATION -> notificationExecutor;
            // CONDITION 은 데이터를 만들어내는 기능이 아니라 흐름을 가르는 제어 노드라서
            // FunctionExecutor 가 아니라 WorkflowService 가 직접 판정한다.
            case CONDITION -> throw new IllegalArgumentException("CONDITION 노드는 FunctionExecutor 로 실행할 수 없습니다.");
        };
    }
}
