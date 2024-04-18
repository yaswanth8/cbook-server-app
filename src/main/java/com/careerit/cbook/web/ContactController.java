package com.careerit.cbook.web;

import com.careerit.cbook.domain.Contact;
import com.careerit.cbook.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/greet")
    public ResponseEntity<String> greetings(){
        return ResponseEntity.ok("Welcome to contact application");
    }

    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact){
        return ResponseEntity.ok(contactService.addContact(contact));
    }

    @PutMapping
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact){
        return ResponseEntity.ok(contactService.updateContact(contact));
    }

    @DeleteMapping("/{cid}")
    public ResponseEntity<String> deleteContact(@PathVariable("cid") long cid){
        boolean isDeleted = contactService.deleteContact(cid);
        if(isDeleted){
            return ResponseEntity.ok("Contact deleted successfully");
        }else{
            return ResponseEntity.ok("Contact not found");
        }
    }

    @GetMapping("/{cid}")
    public ResponseEntity<Contact> getContact(@PathVariable("cid") long cid){
        return ResponseEntity.ok(contactService.getContact(cid));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Contact>> getContacts(){
        return ResponseEntity.ok(contactService.getContacts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Contact>> search(@RequestParam("str") String str){
        return ResponseEntity.ok(contactService.search(str));
    }

}
