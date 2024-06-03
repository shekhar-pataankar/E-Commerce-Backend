package com.UserAuthentication1.Controllers;

import com.UserAuthentication1.DTOS.RequestLoginDto;
import com.UserAuthentication1.DTOS.RequestSignupDto;
import com.UserAuthentication1.Models.Token;
import com.UserAuthentication1.Models.User;
import com.UserAuthentication1.Models.UserDetails;
import com.UserAuthentication1.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    UserService userService;
    @PostMapping("/signup")
    public User signup(@RequestBody RequestSignupDto requestSignupDto){

        return userService.signup(requestSignupDto.getName(),
                requestSignupDto.getEmail(), requestSignupDto.getPassword());
    }

    @PostMapping("/login")
    public Token login(@RequestBody RequestLoginDto requestLoginDto){

        return userService.login(requestLoginDto.getEmail(), requestLoginDto.getPassword());
    }

    @PostMapping("/validatetoken/{token}")
    public boolean validateToken(@PathVariable("token") String token){

        return userService.validateToken(token);

    }

    @PostMapping("/logout/{token}")
    public ResponseEntity<Void> logout(@PathVariable ("token") String token){

        userService.logout(token);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping("/shekhar")
    public UserDetails getName(){

        return new UserDetails("shekhar", "Krishna Nagar Berkhera Pathani, Bhopal");

    }
}
