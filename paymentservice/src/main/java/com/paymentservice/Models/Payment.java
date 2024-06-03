package com.paymentservice.Models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Payment extends BaseModel {

    private Long amount;
    private Long orderId;
    private Long userId;
    private String paymentGatewayReferenceId;
    private String paymentUrl;
    private PaymentStatus paymentStatus;
    private PaymentGateway paymentGateway;
}
