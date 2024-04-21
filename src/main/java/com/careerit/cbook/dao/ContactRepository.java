package com.careerit.cbook.dao;

import com.careerit.cbook.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact,UUID> {



    @Query("select c from Contact c where c.mobile = :mobile and c.deleted = false")
    Optional<Contact> findByMobile(@Param("mobile") String mobile);

    @Query("select c from Contact c where c.name like %:str% or c.email like %:str% or c.mobile like %:str% or c.city like %:str% and deleted = false")
    List<Contact> search(@Param("str") String str);

    @Query("select c from Contact c where c.deleted = false")
    List<Contact> findAll();

    @Query("select c from Contact c where c.id = :id and c.deleted = false")
    Optional<Contact> findById(@Param("id") UUID id);

}
