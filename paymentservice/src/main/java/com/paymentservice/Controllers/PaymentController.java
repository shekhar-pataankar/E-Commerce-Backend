package com.paymentservice.Controllers;

import com.paymentservice.Dtos.CreatePaymentLinkRequestDto;
import com.paymentservice.Dtos.CreatePaymentLinkResponseDto;
import com.paymentservice.Models.PaymentStatus;
import com.paymentservice.Services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

/*
* Here we are creating two endpoints
* 1. createPaymentLink - it will take the orderId as an input and return the Payment URL corresponding the orderid.
* 2. getPaymentStatus - it will take the paymentReferenceId(id corresponding to the link) and payment id to fetch the status of the payment.
* */

    PaymentService paymentService;

    public PaymentController (PaymentService paymentService){

        this.paymentService = paymentService;

    }

    @PostMapping()
    public CreatePaymentLinkResponseDto createPaymentLink (@RequestBody CreatePaymentLinkRequestDto request){

        String url = this.paymentService.createPaymentLink(request.getOrderId());
        CreatePaymentLinkResponseDto responseDto = new CreatePaymentLinkResponseDto();
        responseDto.setUrl(url);

        return responseDto;
    }

    @GetMapping("/status")
    public PaymentStatus getPaymentStatus (@RequestParam String paymentGatwayReferenceId,
                                           @RequestParam String paymentId){

        //return paymentService.getPaymentStatus(paymentGatwayReferenceId, paymentId);
        return null;
    }
}
