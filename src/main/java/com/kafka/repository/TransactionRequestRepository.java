package com.kafka.repository;

import com.kafka.model.TransactionRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRequestRepository extends CrudRepository<TransactionRequest, String> {
}
