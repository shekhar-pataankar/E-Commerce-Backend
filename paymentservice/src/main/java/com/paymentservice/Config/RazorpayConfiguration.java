package com.paymentservice.Config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class RazorpayConfiguration {

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;
    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {

        return new RazorpayClient(razorpayKeyId, razorpayKeySecret);
    }
}
