package com.datajoy.core.exception;

import lombok.Getter;

@Getter
public class BusinessException extends Exception {
    private Integer status;
    private String code;
    private String msg;

    public BusinessException(ErrorMessage e) {
        this.status = e.getStatus();
        this.code = e.getCode();
        this.msg = e.getMsg();
    }
}
