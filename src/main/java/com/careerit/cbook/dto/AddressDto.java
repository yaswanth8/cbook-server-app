package com.careerit.cbook.dto;



import lombok.*;


import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto extends BaseEntityDto{


    private UUID id;

    private String city;

    private String state;

    private String country;

    private String zipCode;
}
