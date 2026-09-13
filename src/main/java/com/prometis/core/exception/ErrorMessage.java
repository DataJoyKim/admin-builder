package com.prometis.core.exception;

public interface ErrorMessage {
    Integer getStatus();

    String getCode();

    String getMsg();
}
