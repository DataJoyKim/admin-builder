package com.datajoy.admin_builder.workflow;

import com.datajoy.admin_builder.message.RequestMessage;
import com.datajoy.admin_builder.message.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WorkflowController {
    @Autowired
    WorkflowService workflowService;

    @PostMapping("/workflow/{workflowName}")
    public ResponseEntity<?> workflow(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse,
            @PathVariable("workflowName") String workflowName,
            @RequestBody RequestMessage requestMessage
    ) {
        ResponseMessage responseMessage = workflowService.execute(httpRequest, httpResponse, workflowName, requestMessage);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatus()));
    }
}
