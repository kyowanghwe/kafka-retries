package com.kafka.message.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public abstract class BaseProducerService<T> {

    protected final KafkaTemplate<String, T> kafkaTemplate;

    public void send(T data) {
        Message<T> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, this.getTopicName())
                .build();

//        Spring version 2.17.4
//        ListenableFuture<SendResult<String, T>> future = kafkaTemplate.send(message);
//        future.addCallback(new ListenableFutureCallback<>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                log.error("Unable to send message : " + message, ex);
//                handleFailure();
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, T> result) {
//                log.info("Sent message: " + message
//                        + " with offset: " + result.getRecordMetadata().offset());
//            }
//        });

//        Spring version 3.1.3
        CompletableFuture<SendResult<String, T>> future = kafkaTemplate.send(message);
        future.whenComplete((result, throwable) -> {
            if (throwable != null) {
                log.error("Unable to send message: " + message, throwable);
                handleFailure();
            } else {
                log.info("Sent message: " + message
                        + " with offset: " + result.getRecordMetadata().offset()
                        + " partition: " + result.getRecordMetadata().partition());
            }
        });
    }

    protected abstract String getTopicName();

    protected abstract void handleFailure();
}
