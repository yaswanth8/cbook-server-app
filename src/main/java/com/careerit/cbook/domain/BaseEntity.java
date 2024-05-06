package com.careerit.cbook.domain;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private boolean deleted;
    @Column(name="created_date",updatable = false,nullable = false)
    private LocalDateTime createdDate;
    @Column(name="modified_date",nullable = false)
    private LocalDateTime modifiedDate;


    @PrePersist
    public void onPrePersist(){
        this.createdDate=LocalDateTime.now();
        this.modifiedDate =LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate(){

        this.modifiedDate =LocalDateTime.now();
    }
}
