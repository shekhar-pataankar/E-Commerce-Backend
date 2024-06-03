package com.UserAuthentication1.Repository;

import com.UserAuthentication1.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository <Token, Long>{

    Optional<Token> findByValueAndIsDeletedEqualsAndExpiredAtGreaterThan(String token, Boolean value, Date date);

    Optional<Token> findByValueAndIsDeleted(String token, Boolean value);


}
