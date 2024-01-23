package com.kafka.service;

import com.kafka.model.TransactionRequest;
import com.kafka.repository.TransactionRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.kafka.enums.TransactionStatus.CONFIRMED;

@Slf4j
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class KafkaRetryTest {
    @Autowired
    private TransactionRequestRepository transactionRequestRepository;

    @Test
    void initTransaction(){
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .phoneNumber("254986890007")
                .transactionRequestId("08d97d43-c1f3-42c1-b8cc-6b1f7e5a8b44")
                .status(CONFIRMED)
                .build();
        transactionRequestRepository.save(transactionRequest);
        System.out.println("init success");
    }

}
