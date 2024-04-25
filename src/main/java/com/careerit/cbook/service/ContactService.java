package com.careerit.cbook.service;

import com.careerit.cbook.domain.Contact;
import com.careerit.cbook.dto.ContactDto;

import java.util.List;
import java.util.UUID;

public interface ContactService {


     ContactDto addContact(ContactDto contact);
     Contact updateContact(Contact contact);
     boolean deleteContact(UUID cid);
     ContactDto getContact(UUID cid);
     List<Contact> getContacts();
     List<Contact> search(String str);

}
