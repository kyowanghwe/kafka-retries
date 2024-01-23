package com.kafka.model;

import com.kafka.enums.TransactionStatus;
import com.kafka.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("TransactionRequest")
public class TransactionRequest {
    @Id
    @NotNull
    private String phoneNumber;

    private String transactionRequestId;

    private String userId;

    private String narrowTransactionId;

    private BigDecimal creditAmount;

    private BigDecimal debitAmount;

    private String currency;

    private TransactionType transactionType;

    private String description;

    private TransactionStatus status;

    private TransactionStatus previousStatus;

    private Date createdAt;

    private Date updatedAt;
}
