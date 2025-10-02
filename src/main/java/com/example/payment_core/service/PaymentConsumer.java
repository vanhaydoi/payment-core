package com.example.payment_core.service;

import com.example.payment_core.DTO.request.PaymentRequest;
import com.example.payment_core.DTO.response.PaymentResponse;

import com.example.payment_core.entity.Payment;
import com.example.payment_core.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.PersistenceException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentConsumer {

    ObjectMapper objectMapper;
    PaymentTransactionServiceImpl paymentService;
    PartnerApiServiceImpl partnerApiService;

    @RabbitListener(queues = "${spring.rabbitmq.payment.queue}")
    public void consumePaymentRequest(String message) {
        log.info("message: {}", message);
        try {
            // Parse message từ queue
            PaymentRequest paymentRequest = objectMapper.readValue(message, PaymentRequest.class);
            log.info("paymentRequest: {}", paymentRequest);
            // Insert vào database
            try {
                Payment transaction = paymentService.savePaymentTransaction(paymentRequest);
                if (transaction == null) {
                    log.info("Save db failed");
                } else {
                    // Gọi Api của đối tác
                    partnerApiService.callPartnerApi(paymentRequest);
                }
            } catch (Exception e) {
                log.error("Error calling partner API for transaction: ", e);
                // Xử lý lỗi partner API nhưng không requeue message
            }
        } catch (JsonProcessingException e) {
            // Lỗi parse JSON - message không hợp lệ, không nên requeue
            log.error("Invalid JSON message format, rejecting message: {}", message, e);
            throw new AmqpRejectAndDontRequeueException(
                    "Invalid JSON format: " + e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            // Xử lý lỗi vi phạm tính toàn vẹn dữ liệu
            log.error("Lỗi vi phạm tính toàn vẹn dữ liệu: {}", e.getMessage());
            throw new AmqpRejectAndDontRequeueException(
                    "Data integrity violation: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            // Xử lý lỗi chung liên quan đến truy cập dữ liệu (bao gồm các vấn đề như kết nối DB thất bại, DB dừng hoạt động đột ngột)
            log.error("Lỗi truy cập dữ liệu khi lưu giao dịch thanh toán: {}", e.getMessage());
            throw new AmqpRejectAndDontRequeueException(
                    "Data access error: " + e.getMessage(), e);
        } catch (PersistenceException e) {
            // Xử lý lỗi persistence chung từ JPA (bao gồm các vấn đề như entity không hợp lệ hoặc vấn đề flush)
            log.error("Lỗi persistence khi lưu giao dịch: {}", e.getMessage());
            throw new AmqpRejectAndDontRequeueException(
                    "Persistence error: " + e.getMessage(), e);
        } catch (Exception e) {
            // Catch-all cho các lỗi bất ngờ khác
            log.error("Lỗi bất ngờ khi lưu giao dịch: {}", e.getMessage());
            throw new AmqpRejectAndDontRequeueException(
                    "Unexpected error: " + e.getMessage(), e);
        }
    }
}
