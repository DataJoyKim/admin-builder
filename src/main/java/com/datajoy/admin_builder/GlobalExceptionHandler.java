package com.datajoy.admin_builder;

import com.datajoy.admin_builder.dto.ResponseMessage;
import com.datajoy.admin_builder.security.exception.SecurityBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SecurityBusinessException.class)
    public ResponseEntity<?> handleSecurityException(SecurityBusinessException e) {
        ResponseMessage responseMessage = ResponseMessage.createErrorMessage(e.getStatus(), e.getErrorCode(), e.getErrorMsg());
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(e.getStatus()));
    }
}
