package com.ibm.demo.repository;

import com.ibm.demo.model.Transaction;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends MongoRepository<Transaction, ObjectId>, TransactionRepositoryCustom {

}
