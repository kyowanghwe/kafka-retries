package com.kafka.message.consumer;

import com.google.gson.Gson;
import com.kafka.model.TransactionRequest;
import com.kafka.service.TransactionRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.kafka.util.Constant.KafkaGroup.TRANSACTION_RETRY_30M_GROUP;
import static com.kafka.util.Constant.KafkaTopic.TRANSACTION_RETRY_30M_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class Retry30mTransactionStatusConsumer implements BaseConsumerService<String> {

    private final TransactionRequestService transactionRequestService;

    @Override
    @KafkaListener(topics = TRANSACTION_RETRY_30M_TOPIC, groupId = TRANSACTION_RETRY_30M_GROUP)
    @RetryableTopic(
            attempts = "2",
//            backoff = @Backoff(delay = 1000 * 60 * 30) // Delay 30 minute between retries
            backoff = @Backoff(delay = 1000 * 30) // Delay 30 minute between retries
    )
    public void receive(String data) {
        log.info("Check transaction status 30m: {} at = {}", data, new Date());
        TransactionRequest transactionRequest = new Gson().fromJson(data, TransactionRequest.class);
        transactionRequestService.checkTransactionRequestStatus(transactionRequest);
    }

    @DltHandler
    public void handler(String data) {
        log.info("Handle check transaction status 30m: {}", data);
        TransactionRequest transactionRequest = new Gson().fromJson(data, TransactionRequest.class);
        transactionRequestService.setTransactionRequestStatusAfter1Hour(transactionRequest);
    }
}
