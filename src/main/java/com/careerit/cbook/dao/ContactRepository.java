package com.careerit.cbook.dao;

import com.careerit.cbook.domain.Contact;
import com.careerit.cbook.util.SecurityContextUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact,UUID> {



    @Query("select c from Contact c where c.mobile = :mobile and c.userId= :userId and c.deleted = false")
    Optional<Contact> findByMobileAndUserId(@Param("mobile")String mobile, @Param("userId") UUID userId);

    default Optional<Contact> findByMobile(String mobile){
        UUID userId = SecurityContextUtil.getLoginUser().getId();
        return findByMobileAndUserId(mobile,userId);
    }

    @Query("select c from Contact c where (c.name like %:str% or c.email like %:str% or c.mobile like %:str%) and c.deleted = false and c.userId = :userId")
    List<Contact> searchOfUser(@Param("str") String str, @Param("userId") UUID userId);

    default List<Contact> search(String str){
        UUID userId = SecurityContextUtil.getLoginUser().getId();
        return searchOfUser(str,userId);
    }

    @Query("select c from Contact c where c.deleted = false and c.userId = :userId")
    List<Contact> findContactsOfUser(@Param("userId") UUID userId);

    default List<Contact> findAll(){
        UUID userId = SecurityContextUtil.getLoginUser().getId();
        return findContactsOfUser(userId);
    }
    @Query("select c from Contact c where c.id = :contactId and c.deleted = false and c.userId = :userId")
    Optional<Contact> findByIdAndUserId(@Param("contactId") UUID id, @Param("userId") UUID userId);

    default Optional<Contact> findById(@Param("id") UUID id){
        UUID userId = SecurityContextUtil.getLoginUser().getId();
        return findByIdAndUserId(id,userId);
    }

}
