package com.paymentservice.PaymentGatways;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentGatwayFactory {

    private RazorpayPaymentGatway razorpayPaymentGatway;
    private StripePaymentGatway stripePaymentGatway;

    public PaymentGatwayFactory (RazorpayPaymentGatway razorpayPaymentGatway,
                                 StripePaymentGatway stripePaymentGatway){

        this.razorpayPaymentGatway = razorpayPaymentGatway;
        this.stripePaymentGatway = stripePaymentGatway;

    }

    public PaymentGatwayInterface getBestPaymentGatway(){

        // Here we need to put some logic to choose the best paymentGatway.
        // But as of now i'm returning the razorPaymentGatway object

        //        Random ran = new Random();
        //        int value = ran.nextInt();
        //
        //        if (value % 2== 0) return stripePaymentGatway;

        //

        //
        // return razorpayPaymentGatway;

        return stripePaymentGatway;



    }
}
