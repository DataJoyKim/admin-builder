package com.datajoy.core.crypto;

public interface PasswordEncoder {
    String encode(String plainPassword);

    boolean matches(String inputPassword, String hashedPassword);
}
