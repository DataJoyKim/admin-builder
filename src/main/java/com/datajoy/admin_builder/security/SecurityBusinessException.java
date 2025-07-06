package com.datajoy.admin_builder.security;

import lombok.Getter;

@Getter
public class SecurityBusinessException extends Exception {
    private Integer status;
    private String errorCode;
    private String errorMsg;

    public SecurityBusinessException(SecurityErrorMessage e) {
        this.errorMsg = e.getErrorMsg();
        this.status = e.getStatus();
        this.errorCode = e.getErrorCode();
    }
}
