package com.careerit.cbook.service;

import com.careerit.cbook.domain.Contact;

import java.util.List;

public interface ContactService {


     Contact addContact(Contact contact);
     Contact updateContact(Contact contact);
     boolean deleteContact(long cid);
     Contact getContact(long cid);
     List<Contact> getContacts();
     List<Contact> search(String str);

}
