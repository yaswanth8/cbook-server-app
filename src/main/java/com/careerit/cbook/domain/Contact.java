package com.careerit.cbook.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Table(name="contact")
@Entity
@Getter
@Setter
public class Contact extends BaseEntity{

    @Id
    private UUID id;
    private String name;
    private String email;
    private String mobile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;


    @PrePersist
    @Override
    public void onPrePersist(){
        this.id=UUID.randomUUID();
        super.onPrePersist();;
    }

}
