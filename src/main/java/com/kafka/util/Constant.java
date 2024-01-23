package com.kafka.util;

import com.kafka.enums.TransactionStatus;

import java.util.Arrays;
import java.util.List;

public interface Constant {

    interface KafkaTopic {
        String TRANSACTION_RETRY_5M_TOPIC = "transaction-retry-5m-topic";
        String TRANSACTION_RETRY_20M_TOPIC = "transaction-retry-20m-topic";
        String TRANSACTION_RETRY_30M_TOPIC = "transaction-retry-30m-topic";
    }

    interface KafkaGroup {
        String TRANSACTION_RETRY_5M_GROUP = "transaction-retry-5m-group";
        String TRANSACTION_RETRY_20M_GROUP = "transaction-retry-20m-group";
        String TRANSACTION_RETRY_30M_GROUP = "transaction-retry-30m-group";
    }

    interface Transaction {
        List<TransactionStatus> transactionPendingStatus = Arrays.asList(TransactionStatus.INITIAL,
                TransactionStatus.PROCESSING,
                TransactionStatus.WAIT_CALLBACK,
                TransactionStatus.CONFIRMED);
    }

}
