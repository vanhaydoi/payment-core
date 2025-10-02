package com.example.payment_core.service;

import com.example.payment_core.DTO.request.PaymentRequest;
import com.example.payment_core.DTO.response.PaymentResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface PartnerApiService {
    void callPartnerApi(PaymentRequest request);
}
