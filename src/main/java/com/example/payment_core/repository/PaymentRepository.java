package com.example.payment_core.repository;

import com.example.payment_core.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, String>{
}
