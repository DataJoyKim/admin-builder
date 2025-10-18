package com.datajoy.admin_builder.workflow;

import com.datajoy.admin_builder.function.WorkflowFunction;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="WORKFLOW_UQ",columnNames={"WORKFLOW_CODE"})})
@Entity
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String workflowCode;

    @Column(nullable = false, length = 200)
    private String displayName;

    @Column(length = 500)
    private String note;

    @Column(nullable = false)
    private Boolean useAuthValidation;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "WORKFLOW_ID")
    private List<WorkflowFunction> functions = new ArrayList<>();

    public void update(String workflowCode, String displayName, String note, Boolean useAuthValidation) {
        this.workflowCode = workflowCode;
        this.displayName = displayName;
        this.note = note;
        this.useAuthValidation = useAuthValidation;
    }
}
