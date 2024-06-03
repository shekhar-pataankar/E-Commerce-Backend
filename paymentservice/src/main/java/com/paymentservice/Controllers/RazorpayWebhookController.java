package com.paymentservice.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/razorpay")
public class RazorpayWebhookController {

    @PostMapping()
    public void handleWebhookEvents(){}
}
