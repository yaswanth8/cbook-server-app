package com.careerit.cbook.dao;

import com.careerit.cbook.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact,Long> {



    Optional<Contact> findByMobile(String mobile);


    @Query("select c from Contact c where c.name like %:str% or c.email like %:str% or c.mobile like %:str% or c.city like %:str%")
    List<Contact> search(String str);

}
