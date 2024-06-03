package com.paymentservice.PaymentGatways;

import com.razorpay.PaymentLink;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.climate.OrderCreateParams;
import com.stripe.param.common.EmptyParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGatway implements PaymentGatwayInterface{

    @Value("${stripe.key_secret}")
    private String apiKey;

    @Override
    public String createPaymentLink(Long orderId, Long amount, String userName, String userEmail, String userPhone) {

        Stripe.apiKey = apiKey;


        ProductCreateParams productParams = ProductCreateParams.builder()
                .setName("T-shirt")
                .build();

        Product product = null;
        try{
            product = Product.create(productParams);
        }
        catch(StripeException e){
            System.out.println("Something is wrong while creating the product");
            throw  new RuntimeException(e);
        }

        String productId = product.getId();
        PriceCreateParams priceParams =
                PriceCreateParams.builder()
                        .setProduct(productId)
                        .setUnitAmount(amount)
                        .setCurrency("INR")
                        .build();

        Price price = null;

        try{
            price = Price.create(priceParams);
        }
        catch(StripeException e){
            System.out.println("Something is wrong while generating the price");
            throw  new RuntimeException(e);
        }

        String priceId = price.getId();

        String city = "bhopal";

        CustomerCreateParams customerParams = CustomerCreateParams.builder()
                .setName(userName)
                .setEmail(userEmail)
                .setPhone(userPhone)
                .setShipping(
                        CustomerCreateParams.Shipping.builder()
                                .setName("xyz")
                                .setAddress(
                                        CustomerCreateParams.Shipping.Address.builder()
                                                .setLine1("510")
                                                .setPostalCode("462022")
                                                .setCity("bhopal")
                                                .setState("MP")
                                                .setCountry("INDIA")
                                                .build()
                                ).build()
                ).build();

        Customer customer = null;

        try{
            customer = Customer.create(customerParams);
        }
        catch (StripeException e){
            System.out.println("something is wrong while creating the customer");
            throw new RuntimeException(e);
        }

        String customerId = customer.getId();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setCustomer(customerId)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(priceId)
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setSuccessUrl("https://scaler.com")
                        .setCancelUrl("https://scaler.com")
                        .build();

        Session session = null;
        try{
            session = Session.create(params);
        }
        catch(StripeException e){
            System.out.println("Something is wrong while creating the session");
            throw  new RuntimeException(e);
        }

        String paymentLink = session.getUrl();

        return paymentLink;

    }
    @Override
    public String getPaymentStatus(String paymentId) {
        return null;
    }
}
