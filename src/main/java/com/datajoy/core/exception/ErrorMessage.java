package com.datajoy.core.exception;

public interface ErrorMessage {
    Integer getStatus();

    String getCode();

    String getMsg();
}
