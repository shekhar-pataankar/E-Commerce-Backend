package com.UserAuthentication1.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Token extends BaseModel {

    @ManyToOne
    private User user;
    private String value;
    private Date expiredAt;
    private boolean isDeleted;

}
