package com.kafka.controller;

import com.kafka.model.TransactionRequest;
import com.kafka.service.TransactionRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionRequestService transactionRequestService;

    @PostMapping("")
    public void trackingTransactionStatus(@RequestBody TransactionRequest transactionRequest) {
        log.info("POST /transaction phoneNumber={}", transactionRequest.getPhoneNumber());
        transactionRequestService.trackingTransactionStatus(transactionRequest);
    }
    
}
