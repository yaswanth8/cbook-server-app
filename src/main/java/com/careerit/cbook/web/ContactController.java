package com.careerit.cbook.web;


import com.careerit.cbook.dto.ContactDto;
import com.careerit.cbook.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;


    @PostMapping
    public ResponseEntity<ContactDto> addContact(@RequestBody ContactDto contactDto){
        return ResponseEntity.ok(contactService.addContact(contactDto));
    }

    @PutMapping
    public ResponseEntity<ContactDto> updateContact(@RequestBody ContactDto contactDto){
        return ResponseEntity.ok(contactService.updateContact(contactDto));
    }

    @DeleteMapping("/{cid}")
    public ResponseEntity<String> deleteContact(@PathVariable("cid") UUID cid){
        boolean isDeleted = contactService.deleteContact(cid);
        if(isDeleted){
            return ResponseEntity.ok("Contact deleted successfully");
        }else{
            return ResponseEntity.ok("Contact not found");
        }
    }

    @GetMapping("/{cid}")
    public ResponseEntity<ContactDto> getContact(@PathVariable("cid") UUID cid){
        return ResponseEntity.ok(contactService.getContact(cid));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactDto>> getContacts(){
        return ResponseEntity.ok(contactService.getContacts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContactDto>> search(@RequestParam("str") String str){
        return ResponseEntity.ok(contactService.search(str));
    }


    @GetMapping("/greet")
    public String greet(){
        return " Welcome to contact Application";

    }

}
