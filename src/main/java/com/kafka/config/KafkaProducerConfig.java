package com.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.kafka.util.Constant.KafkaTopic.TRANSACTION_RETRY_20M_TOPIC;
import static com.kafka.util.Constant.KafkaTopic.TRANSACTION_RETRY_30M_TOPIC;
import static com.kafka.util.Constant.KafkaTopic.TRANSACTION_RETRY_5M_TOPIC;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.profiles.active:Unknown}")
    private String activeProfile;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        if (!"local".equals(activeProfile)) {
            configProps.put("security.protocol", "SSL");
            configProps.put("ssl.truststore.type", "none");
            configProps.put("endpoint.identification.algorithm", "");
        }

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic transactionRetry5m() {
        return new NewTopic(TRANSACTION_RETRY_5M_TOPIC, 3, (short) 2);
    }

    @Bean
    public NewTopic transactionRetry20m() {
        return new NewTopic(TRANSACTION_RETRY_20M_TOPIC, 3, (short) 2);
    }

    @Bean
    public NewTopic transactionRetry30m() {
        return new NewTopic(TRANSACTION_RETRY_30M_TOPIC, 3, (short) 2);
    }

}
