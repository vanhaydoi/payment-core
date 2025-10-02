package com.example.payment_core.service;

import com.example.payment_core.DTO.request.PaymentRequest;
import com.example.payment_core.DTO.response.PaymentResponse;
import com.example.payment_core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.net.SocketTimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerApiServiceImpl implements PartnerApiService {

    private final RestTemplate restTemplate;


    @Value("${partner.api.url}")
    private String partnerApiUrl;

    @Override
    public void callPartnerApi(PaymentRequest request){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PaymentRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<PaymentResponse> response = restTemplate.exchange(
                    partnerApiUrl,
                    HttpMethod.POST,
                    entity,
                    PaymentResponse.class
            );

            if (response.getStatusCode() == HttpStatus.OK){
                log.info("Save data request in api");
//                return PaymentResponse.builder()
//                        .code(ErrorCode.SUCCESS.getCode())
//                        .message("Save data request in api")
//                        .status("SUCCESS")
//                        .build();
            } else {
                log.info("Unexpected response status: {}", response.getStatusCode());
            }
        } catch (ResourceAccessException e){
            if (e.getCause() instanceof SocketTimeoutException) {
                log.info("Api timeout error", e);
//                return PaymentResponse.builder()
//                        .code("98")
//                        .message("TIMEOUT_ERROR")
//                        .build();
            }
            log.info("Api connection error", e);
//            return PaymentResponse.builder()
//                    .code("97")
//                    .message("CONNECTION_ERROR")
//                    .build();
        } catch (HttpClientErrorException e){
            // Lỗi 4xx
            log.info("Lỗi 4xx");
            log.info("Client error (4xx): {}", e.getStatusCode(), e);
//            return PaymentResponse.builder()
//                    .code("96")
//                    .message("CLIENT_ERROR: " + e.getStatusCode())
//                    .build();
        } catch (HttpServerErrorException e){
            // Lỗi 5xx
            log.info("Lỗi 5xx");
            log.error("Server error (5xx): {}", e.getStatusCode(), e);
//            return PaymentResponse.builder()
//                    .code("95")
//                    .message("SERVER_ERROR: "+ e.getStatusCode())
//                    .build();
        } catch (Exception e){
            // Lỗi không xác định
            log.error("Unknown error", e);
//            return PaymentResponse.builder()
//                    .code(ErrorCode.UNKNOW_ERROR.getCode())
//                    .message(ErrorCode.UNKNOW_ERROR.getMessage())
//                    .build();
        }

    }
}