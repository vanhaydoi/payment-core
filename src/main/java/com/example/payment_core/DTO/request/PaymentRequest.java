package com.example.payment_core.DTO.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {

    @NotBlank
    String tokenKey;

    @NotBlank
    String apiID;

    @NotBlank
    String mobile;

    @NotBlank
    String bankCode;

    @NotBlank
    String accountNo;

    @NotBlank
    String payDate;

    @NotBlank
    String additionalData;

    @NotBlank
    Long debitAmount;

    @NotBlank
    private String respCode;

    @NotBlank
    private String respDesc;

    @NotBlank
    private String traceTransfer;

    @NotBlank
    private String messageType;

    @NotBlank
    private String checkSum;

    @NotBlank
    private String orderCode;

    @NotBlank
    private String userName;

    @NotBlank
    private String realAmount;


    private String promotionCode;

    @NotBlank
    @JsonProperty("addValue")
    private String addValue;
}
