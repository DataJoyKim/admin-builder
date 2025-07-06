package com.datajoy.admin_builder.datasource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectValidation {
    private Boolean result;
    private Exception e;

    public void setErrorStack(Exception e) {
        this.e = e;
    }
}
