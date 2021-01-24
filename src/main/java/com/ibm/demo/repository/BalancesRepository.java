package com.ibm.demo.repository;

import com.ibm.demo.model.Balance;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalancesRepository extends MongoRepository<Balance, ObjectId> {

    Page<Balance> findByAccountNumber(String accountNumber, PageRequest lastUpdateTimestamp);
}
