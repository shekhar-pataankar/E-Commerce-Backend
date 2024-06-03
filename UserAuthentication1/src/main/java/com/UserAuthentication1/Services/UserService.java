package com.UserAuthentication1.Services;

import com.UserAuthentication1.DTOS.SendEmailMessageDto;
import com.UserAuthentication1.Models.Token;
import com.UserAuthentication1.Models.User;
import com.UserAuthentication1.Repository.TokenRepository;
import com.UserAuthentication1.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public User signup(String name, String email, String password){

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        User saveUser = userRepository.save(user);

        SendEmailMessageDto sendEmailMessageDto = new SendEmailMessageDto();
        sendEmailMessageDto.setTo(email);
        sendEmailMessageDto.setFrom("scalersupport@gmail.com");
        sendEmailMessageDto.setSubject("Welcome to the scaler");
        sendEmailMessageDto.setBody("Looking forward to having you in our platform!");

        try{
            kafkaTemplate.send(
                    "sendEmail",
                    objectMapper.writeValueAsString(sendEmailMessageDto)
            );
        }
         catch (JsonProcessingException e) {
            System.out.println("something went wrong while converting the sendEmailMessageDto to string");
            throw new RuntimeException(e);
        }


        return saveUser;
    }

    public Token login(String email, String password) {

        Optional<User> userEmail = userRepository.findByEmail(email);

        if (userEmail.isEmpty()){

            throw new RuntimeException("Invalid email");

        }
        User user = userEmail.get();

        boolean matches = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (!matches){
            throw new RuntimeException("Password is invalid");
        }

        Token token = new Token();
        token.setUser(user);
        token.setValue(UUID.randomUUID().toString());

        Date expiredDate = getExpiryDate();
        token.setExpiredAt(expiredDate);
        token.setDeleted(false);

        Token saveToken = tokenRepository.save(token);
        return saveToken;
    }

    private Date getExpiryDate() {

//        LocalDate futureDate = LocalDate.now().plusDays(30);
//        Date expiredDate = Date.from(Instant.from(futureDate));
//
//        return expiredDate;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);

        Date expiredDate = calendar.getTime();

        return expiredDate;
    }

    public boolean validateToken(String token) {
        /*
        * 1. check if token there.
        * 2. validate if token is deleted.
        * 3. validate the expiry of token.
        * */
        Optional<Token> tokenOptional = tokenRepository.findByValueAndIsDeletedEqualsAndExpiredAtGreaterThan(token,
                false, new Date());

        if (tokenOptional.isEmpty()){

            return false;

        }
        return true;
    }

    public void logout(String token) {
        /*
        * check token in database.
        * check token isDeleted is false/true.
        * */

        Optional<Token> tokenOptional = tokenRepository.findByValueAndIsDeleted(token, false);

        if (tokenOptional.isEmpty()){

            throw  new RuntimeException("Token is not present");

        }
        Token tokenobject = tokenOptional.get();
        tokenobject.setDeleted(true);

        tokenRepository.save(tokenobject);

    }
}
