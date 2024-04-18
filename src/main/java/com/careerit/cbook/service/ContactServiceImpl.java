package com.careerit.cbook.service;

import com.careerit.cbook.dao.ContactRepository;
import com.careerit.cbook.domain.Contact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService{

    private final ContactRepository contactRepository;
    @Override
    public Contact addContact(Contact contact) {
        Assert.notNull(contact,"Contact can't be null");
        Assert.notNull(contact.getName(),"Name can't be null");
        Assert.notNull(contact.getEmail(),"Email can't be null");
        Assert.notNull(contact.getMobile(),"Mobile can't be null");
        if(getExistingContact(contact.getMobile()).isPresent()){
            log.error("Contact with mobile : {} already exists",contact.getMobile());
            throw new ContactExistsException("Contact with mobile : "+contact.getMobile()+" already exists");
        }
        Contact savedContact = contactRepository.save(contact);
        log.info("Contact with mobile : {} saved successfully",contact.getMobile());
        return savedContact;
    }
    @Override
    public Contact updateContact(Contact contact) {
        Assert.notNull(contact,"Contact can't be null");
        Assert.notNull(contact.getCid(),"Contact id can't be null");
        Assert.notNull(contact.getName(),"Name can't be null");
        Assert.notNull(contact.getEmail(),"Email can't be null");

        Optional<Contact> optionalContact = contactRepository.findById(contact.getCid());
        if(optionalContact.isPresent()) {
            Contact existingContact = getExistingContact(contact.getMobile()).orElse(null);
            if(existingContact != null && !existingContact.getCid().equals(contact.getCid())){
                log.error("Contact with mobile : {} already exists",contact.getMobile());
                throw new ContactExistsException("Contact with mobile : "+contact.getMobile()+" already exists");
            }
            Contact savedContact = contactRepository.save(contact);
            log.info("Contact with mobile : {} updated successfully", contact.getMobile());
            return savedContact;
        }else{
            log.error("Contact with id : {} not found",contact.getCid());
            throw new ContactNotFoundException("Contact with id : "+contact.getCid()+" not found");
        }

    }

    @Override
    public boolean deleteContact(long cid) {
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
    public Contact getContact(long cid) {
        Assert.notNull(cid,"Contact id can't be null");
        Optional<Contact> optionalContact = contactRepository.findById(cid);
        if(optionalContact.isPresent()){
            return optionalContact.get();
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
