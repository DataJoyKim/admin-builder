package com.datajoy.admin_builder.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
public class Client {
    private String clientIp;
    private String device;

    public Client(HttpServletRequest request) {
        this.clientIp = request.getRemoteAddr();
    }
}
