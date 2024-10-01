package com.datajoy.persistence;

public class DuplicateException extends RuntimeException {
    public DuplicateException(){
        super("key duplicate save failed...");
    }
}
