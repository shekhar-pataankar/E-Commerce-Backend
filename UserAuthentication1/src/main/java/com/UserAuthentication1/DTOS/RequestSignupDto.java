package com.UserAuthentication1.DTOS;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RequestSignupDto {

    private String name;
    private String email;
    private String password;

}
