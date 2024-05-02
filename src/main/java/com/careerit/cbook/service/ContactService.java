package com.careerit.cbook.service;


import com.careerit.cbook.dto.ContactDto;

import java.util.List;
import java.util.UUID;

public interface ContactService {


     ContactDto addContact(ContactDto contactDto);
     ContactDto updateContact(ContactDto contactDto);
     boolean deleteContact(UUID cid);
     ContactDto getContact(UUID cid);
     List<ContactDto> getContacts();
     List<ContactDto> search(String str);

}
