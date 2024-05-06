package com.careerit.cbook.auth.dto;



import lombok.Data;

@Data
public class LoginUserDto {
    private String username;
    private String password;
}