package com.example.payment_core.service;

import com.example.payment_core.DTO.request.PaymentRequest;
import com.example.payment_core.entity.Payment;

public interface PaymentTransactionService {
    Payment savePaymentTransaction(PaymentRequest request);
}
