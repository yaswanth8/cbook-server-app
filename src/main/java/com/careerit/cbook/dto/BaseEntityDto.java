package com.careerit.cbook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BaseEntityDto {

    private boolean deleted;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
