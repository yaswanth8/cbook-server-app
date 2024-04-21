package com.careerit.cbook.service;

import com.careerit.cbook.domain.Contact;

import java.util.List;
import java.util.UUID;

public interface ContactService {


     Contact addContact(Contact contact);
     Contact updateContact(Contact contact);
     boolean deleteContact(UUID cid);
     Contact getContact(UUID cid);
     List<Contact> getContacts();
     List<Contact> search(String str);

}
