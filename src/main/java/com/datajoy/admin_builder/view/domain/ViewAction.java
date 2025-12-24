package com.datajoy.admin_builder.view.domain;

import com.datajoy.admin_builder.view.code.ActionType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="VIEW_ACTION_UQ",columnNames={"objectCode","actionName"})})
@Entity
public class ViewAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String objectCode;
    @Column(nullable = false, length = 200)
    private String actionName;
    @Column(length = 200)
    private String displayName;
    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ActionType type;

    @Column(length = 100)
    private String workflowCode;
    @Column(length = 100)
    private String workflowRequestMessageId;
    @Column(length = 100)
    private String workflowResponseMessageId;
    @Column(length = 100)
    private String workflowResponseGridId;
    @Lob
    @Column
    private String script;

    public void update(
            String objectCode,
            String actionName,
            String displayName,
            ActionType type,
            String workflowCode,
            String workflowRequestMessageId,
            String workflowResponseMessageId,
            String workflowResponseGridId,
            String script
    ) {
        this.objectCode = objectCode;
        this.actionName = actionName;
        this.displayName = displayName;
        this.type = type;
        this.workflowCode = workflowCode;
        this.workflowRequestMessageId = workflowRequestMessageId;
        this.workflowResponseMessageId = workflowResponseMessageId;
        this.workflowResponseGridId = workflowResponseGridId;
        this.script = script;
    }
}
