package com.paymentservice.Services;

import com.paymentservice.Models.Payment;
import com.paymentservice.Models.PaymentGateway;
import com.paymentservice.Models.PaymentStatus;
import com.paymentservice.PaymentGatways.PaymentGatwayFactory;
import com.paymentservice.PaymentGatways.PaymentGatwayInterface;
import com.paymentservice.PaymentGatways.RazorpayPaymentGatway;
import com.paymentservice.PaymentGatways.StripePaymentGatway;
import com.paymentservice.PaymentRepository.PaymentRepository;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {


    PaymentGatwayFactory paymentGatwayFactory;

    PaymentRepository paymentRepository;

    RazorpayPaymentGatway razorpayPaymentGatway;
    StripePaymentGatway stripePaymentGatway;


    public PaymentService (PaymentGatwayFactory paymentGatwayFactory,
                    PaymentRepository paymentRepository,
                    RazorpayPaymentGatway razorpayPaymentGatway,
                    StripePaymentGatway stripePaymentGatway){

        this.paymentGatwayFactory = paymentGatwayFactory;
        this.paymentRepository = paymentRepository;
        this.razorpayPaymentGatway = razorpayPaymentGatway;
        this.stripePaymentGatway = stripePaymentGatway;

    }


    public String createPaymentLink(Long orderId){

        // Here we will call the order service to fetch the order details.

        Long amount = 100000L;
        String userName = "Shekhar Patankar";
        String userEmail = "shekhar@gmail.com";
        String userPhone = "+919898987868";


        PaymentGatwayInterface paymentGatway = paymentGatwayFactory.getBestPaymentGatway();
        String paymentLink = paymentGatway.createPaymentLink(orderId, amount, userName, userEmail, userPhone);

        //String paymentUrl = paymentLink.get("short_url").toString();

//        Payment payment = new Payment();
//        payment.setPaymentUrl(paymentLink);
//        payment.setAmount(amount);
//        payment.setOrderId(orderId);
//        payment.setPaymentGateway(PaymentGateway.RAZORPAY);
//        payment.setPaymentStatus(PaymentStatus.PENDING);
        //payment.setPaymentGatewayReferenceId(paymentLink.get("id").toString());

        //paymentRepository.save(payment);

        return paymentLink;
    }

    public PaymentStatus getPaymentStatus(String paymentGatwayReferenceId, String paymentId) {

//        Payment payment = paymentRepository.findByPaymentGatwayReferenceId(paymentGatwayReferenceId);
//
//        PaymentGatwayInterface paymentGatway;
//
//        if (payment.getPaymentGateway().equals("RAZORPAY")){
//
//            paymentGatway = razorpayPaymentGatway;
//            PaymentStatus paymentStatus = null;
//            String razorpayPaymentStatus = null;
//
//            try{
//                razorpayPaymentStatus = paymentGatway.getPaymentStatus(paymentId);
//            }
//            catch(RazorpayException e){
//                System.out.println("Fetching the payment status");
//                throw new RuntimeException(e);
//            }
//
//            if (razorpayPaymentStatus.equals("Captured")){
//
//                payment.setPaymentStatus(PaymentStatus.SUCESSFULL);
//                paymentStatus = PaymentStatus.SUCESSFULL;
//
//            }
//            else if (razorpayPaymentStatus.equals("Failed")){
//
//                payment.setPaymentStatus(PaymentStatus.FAILED);
//                paymentStatus = PaymentStatus.FAILED;
//            }
//
//            paymentRepository.save(payment);
//
//            return paymentStatus;
//        }
//        else if (payment.getPaymentGateway().equals("STRIPE"))
//        {
//
//        }
//        return null;

        return null;
    }
}
