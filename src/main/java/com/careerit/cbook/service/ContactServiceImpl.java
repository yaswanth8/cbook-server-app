package com.careerit.cbook.service;

import com.careerit.cbook.dao.AddressRepository;
import com.careerit.cbook.dao.ContactRepository;
import com.careerit.cbook.domain.Address;
import com.careerit.cbook.domain.Contact;
import com.careerit.cbook.dto.AddressDto;
import com.careerit.cbook.dto.ContactDto;
import com.careerit.cbook.util.ConvertorUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService{

    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;
    @Override
    @Transactional
    public ContactDto addContact(ContactDto contactDto) {
        Assert.notNull(contactDto,"Contact can't be null");
        Assert.notNull(contactDto.getName(),"Name can't be null");
        Assert.notNull(contactDto.getEmail(),"Email can't be null");
        Assert.notNull(contactDto.getMobile(),"Mobile can't be null");
        if(getExistingContact(contactDto.getMobile()).isPresent()){
            log.error("Contact with mobile : {} already exists",contactDto.getMobile());
            throw new ContactExistsException("Contact with mobile : "+contactDto.getMobile()+" already exists");
        }
        AddressDto addressDto = contactDto.getAddress();
        if(addressDto != null){
            Address address = new Address();
            address.setCity(addressDto.getCity());
            address.setState(addressDto.getState());
            address.setCountry(addressDto.getCountry());
            address.setZipCode(addressDto.getZipCode());
            address = addressRepository.save(address);
            addressDto.setId(address.getId());
            Contact contact = new Contact();
            contact.setName(contactDto.getName());
            contact.setEmail(contactDto.getEmail());
            contact.setMobile(contactDto.getMobile());
            contact.setAddress(address);
            contact = contactRepository.save(contact);
            contactDto.setId(contact.getId());
            log.info("Contact with id : {} and mobile : {} added successfully",contact.getId(), contact.getMobile());
        }
        return contactDto;
    }
    @Override
    public Contact updateContact(Contact contact) {
        Assert.notNull(contact,"Contact can't be null");
        Assert.notNull(contact.getId(),"Contact id can't be null");
        Assert.notNull(contact.getName(),"Name can't be null");
        Assert.notNull(contact.getEmail(),"Email can't be null");

        Optional<Contact> optionalContact = contactRepository.findById(contact.getId());
        if(optionalContact.isPresent()) {
            Contact existingContact = getExistingContact(contact.getMobile()).orElse(null);
            if(existingContact != null && !existingContact.getId().equals(contact.getId())){
                log.error("Contact with mobile : {} already exists",contact.getMobile());
                throw new ContactExistsException("Contact with mobile : "+contact.getMobile()+" already exists");
            }
            Contact savedContact = contactRepository.save(contact);
            log.info("Contact with mobile : {} updated successfully", contact.getMobile());
            return savedContact;
        }else{
            log.error("Contact with id : {} not found",contact.getId());
            throw new ContactNotFoundException("Contact with id : "+contact.getId()+" not found");
        }

    }

    @Override
    public boolean deleteContact(UUID cid) {
        Assert.notNull(cid,"Contact id can't be null");
        Optional<Contact> optionalContact = contactRepository.findById(cid);
        if(optionalContact.isPresent()){
            contactRepository.deleteById(cid);
            log.info("Contact with id : {} deleted successfully",cid);
            return true;
        }else{
            log.error("Contact with id : {} not found",cid);
            throw new ContactNotFoundException("Contact with id : "+cid+" not found");
        }
    }

    @Override
    public ContactDto getContact(UUID cid) {
        Assert.notNull(cid,"Contact id can't be null");
        Optional<Contact> optionalContact = contactRepository.findById(cid);
        if(optionalContact.isPresent()){
            Contact contact = optionalContact.get();
            ContactDto contactDto = ConvertorUtil.domainToDto(contact,ContactDto.class);
            log.info("Contact with id : {} found",cid);
            return contactDto;
        }else{
            log.error("Contact with id : {} not found",cid);
            throw new ContactNotFoundException("Contact with id : "+cid+" not found");
        }
    }

    @Override
    public List<Contact> getContacts() {
        List<Contact> contacts =  contactRepository.findAll();
        log.info("Total contacts found : {}",contacts.size());
        return contacts;
    }


    @Override
    public List<Contact> search(String str) {
        log.info("Searching for : {}",str);
        List<Contact> contacts =  contactRepository.search(str);
        log.info("For given search string : {} found : {}",str,contacts.size());
        return contacts;
    }


    private Optional<Contact> getExistingContact(String mobile){
        return contactRepository.findByMobile(mobile);
    }
}
