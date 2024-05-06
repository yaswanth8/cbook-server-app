package com.careerit.cbook.auth.repo;



import com.careerit.cbook.auth.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID>{

    @Query("select u from AppUser u where u.username = :username and u.deleted = false")
    Optional<AppUser> findByUsername(@Param("username") String username);
}