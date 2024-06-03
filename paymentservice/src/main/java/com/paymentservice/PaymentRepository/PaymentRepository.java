package com.paymentservice.PaymentRepository;

import com.paymentservice.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    //Payment findByPaymentGatwayReferenceId(String paymentGatwayReferenceId);


    @Override
    List<Payment> findAll();
}
