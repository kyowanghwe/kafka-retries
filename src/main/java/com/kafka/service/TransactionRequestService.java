package com.kafka.service;

import com.google.gson.Gson;
import com.kafka.message.producer.CheckTransactionStatusProducerAfter5m;
import com.kafka.model.TransactionRequest;
import com.kafka.repository.TransactionRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.kafka.enums.TransactionStatus.FAILED;
import static com.kafka.util.Constant.Transaction.transactionPendingStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionRequestService {
    private final TransactionRequestRepository transactionRequestRepository;
    private final CheckTransactionStatusProducerAfter5m checkTransactionStatusProducerAfter5m;
    private final Gson gson = new Gson();

    public void trackingTransactionStatus(TransactionRequest transactionRequest){
        checkTransactionStatusProducerAfter5m.send(gson.toJson(transactionRequest));
        log.info("Kafka has pushed successfully to {}, ID={}", checkTransactionStatusProducerAfter5m.getTopicName(), transactionRequest.getTransactionRequestId());
    }

    public void checkTransactionRequestStatus(TransactionRequest transactionRequest) {
        transactionRequestRepository.findById(transactionRequest.getPhoneNumber()).ifPresent(transaction ->{
            log.info("Transaction found, ID={} status={}", transaction.getTransactionRequestId(), transaction.getStatus());
            if (transactionPendingStatus.contains(transaction.getStatus())) {
                throw new RuntimeException("Transaction status=" + transaction.getStatus() + ", continue to retry");
            }
            log.info("Transaction has been processed ID={} status={}", transaction.getTransactionRequestId(), transaction.getStatus());
        });
        log.info("Transaction not found ID={}", transactionRequest.getTransactionRequestId());
    }

    public void setTransactionRequestStatusAfter1Hour(TransactionRequest transactionRequest) {
        log.info("Start set transaction to FAILED");
        transactionRequestRepository.findById(transactionRequest.getPhoneNumber()).ifPresent(transaction ->{
            transaction.setStatus(FAILED);
            transactionRequestRepository.save(transaction);
            log.info("Update transactionId={} to FAILED at {}", transaction.getTransactionRequestId(), new Date());
        });
    }
}
