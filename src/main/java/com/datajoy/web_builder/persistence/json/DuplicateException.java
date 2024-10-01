package com.datajoy.web_builder.persistence.json;

public class DuplicateException extends RuntimeException {
    public DuplicateException(){
        super("key duplicate save failed...");
    }
}
