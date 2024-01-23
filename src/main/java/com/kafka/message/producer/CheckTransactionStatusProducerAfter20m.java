package com.kafka.message.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.kafka.util.Constant.KafkaTopic.TRANSACTION_RETRY_20M_TOPIC;


@Slf4j
@Component
public class CheckTransactionStatusProducerAfter20m extends BaseProducerService<String>{

    public CheckTransactionStatusProducerAfter20m(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public String getTopicName() {
        return TRANSACTION_RETRY_20M_TOPIC;
    }

    @Override
    protected void handleFailure() {

    }
}
