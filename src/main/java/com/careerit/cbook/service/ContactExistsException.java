package com.careerit.cbook.service;

public class ContactExistsException extends RuntimeException {
    public ContactExistsException(String s) {
        super(s);
    }
}
