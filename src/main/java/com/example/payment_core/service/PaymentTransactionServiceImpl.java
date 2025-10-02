package com.example.payment_core.service;

import com.example.payment_core.DTO.request.PaymentRequest;
import com.example.payment_core.entity.Payment;
import com.example.payment_core.mapper.PaymentMapper;
import com.example.payment_core.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentTransactionServiceImpl implements PaymentTransactionService{
    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;

    @Override
    public Payment savePaymentTransaction(PaymentRequest request){
        log.info("Khi chưa mapping: {}", request);
        Payment result = paymentMapper.toPayment(request);
        log.info("result(Khi đã mapping): {}", result);
        return paymentRepository.save(result);
    }
}
