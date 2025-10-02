package com.example.payment_core.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    SUCCESS("00", "Success"),
    INVALID("01", "Invalid request - required field is missing"),
    BANK_CODE("02", "Bank code not found"),
    INVALID_CHECKSUM("03", "Invalid checksum"),
    UNKNOW_ERROR("04", "Unknow error"),
    TOKEN_KEY_DUPLICATED("05", "Token key is duplicated"),
    SYSTEM_UNDER_MAINTENANCE("96", "System under maintenance"),
    SUSPICIOUS_TRANSACTION("08", "Suspicious transaction"),
    DATA_NOT_FOUND("09", "Data not found"),
    REDIS_CONN("10","Failed to connect to Redis server"),
    REDIS_TIMEOUT("11", "Redis operation timeout"),
    REDIS_SYSTEM_ERROR("12", "Redis system error"),
    CHECK_REAL_AMOUNT("13", "The realAmount value cannot be greater than the debitAmount"),
    RABBITMQ_CONNECTION_ERROR("14", "Failed to connect to RabbitMQ server"),
    RABBITMQ_RESOURCE_ERROR("15", "RabbitMQ resource not available"),
    RABBITMQ_TIMEOUT_ERROR("16", "RabbitMQ operation timeout"),
    RABBITMQ_AUTH_ERROR("17", "RabbitMQ authentication failed"),
    RABBITMQ_IO_ERROR("18", "RabbitMQ I/O error. Communication error with message queue"),
    RABBITMQ_ERROR("19", "General RabbitMQ error. Message queue error occurred"),
    SAVE_DATA_FAILED("20","Save data failed"),
    DATA_INTEGRITY_VIOLATION("94", "Data integrity violation"),
    DATA_ACCESS_ERROR("93", "Data access error while saving payment transaction"),
    PERSISTENCE_ERROR("92", "Persistence error while saving payment transaction"),
    INVALID_JSON_MESSAGE("91", "Invalid JSON message format")
    ;

    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

}
