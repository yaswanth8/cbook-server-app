package com.careerit.cbook.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name="contact_address")
public class Address extends BaseEntity{

    @Id
    private UUID id;
    @Column(name="city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "country")
    private String country;
    @Column(name = "zip_code")
    private String zipCode;

    @PrePersist
    @Override
    public void onPrePersist() {
        this.id = UUID.randomUUID();
        super.onPrePersist();
    }
}
