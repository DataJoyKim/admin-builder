package com.datajoy.admin_builder.security;

public interface PasswordEncoder {
    String encode(String plainPassword);

    boolean matches(String inputPassword, String hashedPassword);
}
