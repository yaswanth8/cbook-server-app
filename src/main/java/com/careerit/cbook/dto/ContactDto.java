package com.careerit.cbook.dto;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDto extends BaseEntityDto {

    private UUID id;
    private String name;
    private String email;
    private String mobile;
    private AddressDto address;
}
