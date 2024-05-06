package com.careerit.cbook.service;

import com.careerit.cbook.auth.domain.AppUser;
import com.careerit.cbook.dao.AddressRepository;
import com.careerit.cbook.dao.ContactRepository;
import com.careerit.cbook.domain.Address;
import com.careerit.cbook.domain.Contact;
import com.careerit.cbook.dto.AddressDto;
import com.careerit.cbook.dto.ContactDto;
import com.careerit.cbook.util.ConvertorUtil;
import com.careerit.cbook.util.SecurityContextUtil;
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

    public static final String CONTACT_CAN_T_BE_NULL ="Contact can't be null";
    public static final String CONTACT_ID_CAN_T_BE_NULL = "Contact id can't be null";
    public static final String CONTACT_WITH_ID_NOT_FOUND = "Contact with id : {} not found";
    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;
    @Override
    @Transactional
    public ContactDto addContact(ContactDto contactDto) {
        Assert.notNull(contactDto,CONTACT_CAN_T_BE_NULL);
        Assert.notNull(contactDto.getName(),"Name can't be null");
        Assert.notNull(contactDto.getEmail(),"Email can't be null");
        Assert.notNull(contactDto.getMobile(),"Mobile can't be null");
        if(getExistingContact(contactDto.getMobile()).isPresent()){
            log.error("Contact with mobile : {} already exists",contactDto.getMobile());
            throw new ContactExistsException("Contact with mobile : "+contactDto.getMobile()+" already exists");
        }
        AddressDto addressDto = contactDto.getAddress();
        Contact contact = ConvertorUtil.dtoToDomain(contactDto,Contact.class);
        if(addressDto != null){
            AppUser appUser= SecurityContextUtil.getLoginUser();
            contact.setUserId(appUser.getId());
            Address address = ConvertorUtil.dtoToDomain(addressDto,Address.class);
            address = addressRepository.save(address);
            contact.setAddress(address);
            contact = contactRepository.save(contact);
            log.info("Contact with id : {} and mobile : {} added successfully",contact.getId(), contact.getMobile());
        }
        return ConvertorUtil.domainToDto(contact,ContactDto.class);
    }
    @Override
    public ContactDto updateContact(ContactDto contactDto) {
        Assert.notNull(contactDto,CONTACT_CAN_T_BE_NULL);
        Assert.notNull(contactDto.getId(), CONTACT_ID_CAN_T_BE_NULL);
        Assert.notNull(contactDto.getName(),"Name can't be null");
        Assert.notNull(contactDto.getEmail(),"Email can't be null");

        Optional<Contact> optionalContact = contactRepository.findById(contactDto.getId());
        if(optionalContact.isPresent()) {
            Contact existingContact = getExistingContact(contactDto.getMobile()).orElse(null);
            if(existingContact != null && !existingContact.getId().equals(contactDto.getId())){
                log.error("Contact with mobile : {} already exists",contactDto.getMobile());
                throw new ContactExistsException("Contact with mobile : "+contactDto.getMobile()+" already exists");
            }
            Contact contact=ConvertorUtil.dtoToDomain(contactDto,Contact.class);
            AddressDto addressDto=contactDto.getAddress();
            if(addressDto != null){
                Address address = ConvertorUtil.dtoToDomain(addressDto,Address.class);
                address = addressRepository.save(address);
                contact.setAddress(address);
            }
            Contact savedContact = contactRepository.save(contact);
            ContactDto updatedContactDto = ConvertorUtil.domainToDto(savedContact,ContactDto.class);
            log.info("Contact with id : {} is updated successfully", updatedContactDto.getId());
            return updatedContactDto;

        }else{
            log.error(CONTACT_WITH_ID_NOT_FOUND,contactDto.getId());
            throw new ContactNotFoundException("Contact with id : "+contactDto.getId()+" not found");
        }

    }

    @Override
    public boolean deleteContact(UUID cid) {
        Assert.notNull(cid,CONTACT_ID_CAN_T_BE_NULL);
        Optional<Contact> optionalContact = contactRepository.findById(cid);
        if(optionalContact.isPresent()){
            contactRepository.deleteById(cid);
            log.info("Contact with id : {} deleted successfully",cid);
            return true;
        }else{
            log.error(CONTACT_WITH_ID_NOT_FOUND,cid);
            throw new ContactNotFoundException("Contact with id : "+cid+" not found");
        }
    }

    @Override
    public ContactDto getContact(UUID cid) {
        Assert.notNull(cid,CONTACT_ID_CAN_T_BE_NULL);
        Optional<Contact> optionalContact = contactRepository.findById(cid);
        if(optionalContact.isPresent()){
            Contact contact = optionalContact.get();
            ContactDto contactDto = ConvertorUtil.domainToDto(contact,ContactDto.class);
            log.info("Contact with id : {} found",cid);
            return contactDto;
        }else{
            log.error(CONTACT_WITH_ID_NOT_FOUND,cid);
            throw new ContactNotFoundException("Contact with id : "+cid+" not found");
        }
    }

    @Override
    public List<ContactDto> getContacts() {
        List<Contact> contacts =  contactRepository.findAll();
        log.info("Total contacts found : {}",contacts.size());
        return contacts.stream().map(contact -> ConvertorUtil.domainToDto(contact,ContactDto.class)).toList();

    }


    @Override
    public List<ContactDto> search(String str) {
        log.info("Searching for : {}",str);
        List<Contact> contacts =  contactRepository.search(str);
        log.info("For given search string : {} found : {}",str,contacts.size());
        return contacts.stream().map(contact -> ConvertorUtil.domainToDto(contact,ContactDto.class)).toList();
    }


    private Optional<Contact> getExistingContact(String mobile){
        return contactRepository.findByMobile(mobile);
    }
}
