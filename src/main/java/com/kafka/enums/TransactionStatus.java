package com.kafka.enums;

public enum TransactionStatus {
    INITIAL,
    PROCESSING,
    WAIT_CALLBACK,
    CONFIRMED,
    SUCCESS,
    FAILED
}
