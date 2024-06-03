package com.paymentservice.PaymentGatways;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import org.springframework.context.annotation.Configuration;

@Configuration
public interface PaymentGatwayInterface {

    public String createPaymentLink(Long orderId, Long amount, String userName, String userEmail, String userPhone);
    public String getPaymentStatus(String paymentId) throws RazorpayException;

}
