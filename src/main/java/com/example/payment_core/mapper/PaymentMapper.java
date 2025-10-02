package com.example.payment_core.mapper;

import com.example.payment_core.DTO.request.PaymentRequest;
import com.example.payment_core.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {


    Payment toPayment(PaymentRequest request);
}
