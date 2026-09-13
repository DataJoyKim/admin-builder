package com.prometis.appbuilder.node;

import com.prometis.appbuilder.node.code.ErrorResolveType;
import com.prometis.appbuilder.node.code.FunctionType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class WorkflowNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "WORKFLOW_ID", nullable = false)
    private Long workflowId;

    // 워크플로우 안에서 노드를 식별하는 값. WorkflowEdge 가 이 값으로 노드를 연결한다.
    // 저장 시 노드를 전부 지우고 다시 넣기 때문에 DB id 는 매번 바뀐다. 그래서 연결정보는 id 가 아니라 nodeId 를 쓴다.
    @Column(length = 100)
    private String nodeId;

    @Column(nullable = false, length = 100)
    private String functionName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private FunctionType functionType;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ErrorResolveType errorResolveType;

    // 연결정보가 없는 예전 워크플로우를 직선 흐름으로 해석할 때의 순서이자, 빌더 화면의 표시 순서.
    @Column
    private Integer orderNum;

    @Column
    private Boolean isLogging;

    @Column(length = 100)
    private String requestMessageId;

    @Column(length = 100)
    private String responseMessageId;

    // 빌더 캔버스에서의 노드 위치. 실행에는 쓰이지 않고, 화면을 다시 열었을 때 배치를 그대로 되살리는 용도다.
    @Column
    private Integer positionX;

    @Column
    private Integer positionY;

    public boolean isCondition() {
        return FunctionType.CONDITION.equals(functionType);
    }

    public void update(
            Long workflowId,
            String nodeId,
            String functionName,
            FunctionType functionType,
            Integer orderNum,
            Boolean isLogging,
            String requestMessageId,
            String responseMessageId,
            Integer positionX,
            Integer positionY
    ) {
        this.workflowId = workflowId;
        this.nodeId = nodeId;
        this.functionName = functionName;
        this.functionType = functionType;
        this.orderNum = orderNum;
        this.isLogging = isLogging;
        this.requestMessageId = requestMessageId;
        this.responseMessageId = responseMessageId;
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
