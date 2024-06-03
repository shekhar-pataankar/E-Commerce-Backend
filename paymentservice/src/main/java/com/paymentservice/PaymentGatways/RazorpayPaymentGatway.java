package com.paymentservice.PaymentGatways;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayPaymentGatway implements PaymentGatwayInterface{

    RazorpayClient razorpayClient;

    public RazorpayPaymentGatway(RazorpayClient razorpayClient){
        this.razorpayClient = razorpayClient;
    }
    @Override
    public String createPaymentLink(Long orderId, Long amount, String userName, String userEmail, String userPhone) {

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");
        //paymentLinkRequest.put("accept_partial",true);
        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",System.currentTimeMillis()/1000 + 30 * 60); //
        paymentLinkRequest.put("reference_id",orderId.toString());
        paymentLinkRequest.put("description","Payment for policy no #23456");
        JSONObject customer = new JSONObject();
        customer.put("name",userName);
        customer.put("contact",userPhone);
        customer.put("email",userEmail);
//        paymentLinkRequest.put("customer",customer);
//        JSONObject notify = new JSONObject();
//        notify.put("sms",true);
//        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
       // notes.put("policy_name","Jeevan Bima");
        //paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://scaler.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = null;
        try{
            payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        }
        catch (RazorpayException e) {
            System.out.println("Error while creating the payment link via razorpayclient");
            throw new RuntimeException(e);
        }
        return payment.get("short_url").toString();
    }

    @Override
    public String getPaymentStatus(String paymentId) throws RazorpayException {

//        Payment razorpayPayment = razorpayClient.payments.fetch(paymentId);
//
//        String paymentStatus = razorpayPayment.get("status").toString();
//
//        return paymentStatus;
        return null;

    }
}
