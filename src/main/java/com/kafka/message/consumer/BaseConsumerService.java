package com.kafka.message.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface BaseConsumerService<T> {

    void receive(T data) throws JsonProcessingException;
}
