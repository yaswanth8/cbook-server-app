package com.careerit.cbook.service;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String s) {

        super(s);
    }
}
