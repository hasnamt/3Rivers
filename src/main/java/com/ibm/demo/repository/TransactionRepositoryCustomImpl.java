package com.ibm.demo.repository;

import com.ibm.demo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.Optional;

public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Transaction> findByAccountNumberAndType(String accountNumber, String type,
                                                        LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable) {

        Query query = new Query().with(pageable);

        query.addCriteria(Criteria.where("accountNumber").is(accountNumber));
        Optional.ofNullable(type).ifPresent(txType -> query.addCriteria(Criteria.where("type").is(txType)));

        if (fromDate != null) {
            query.addCriteria(new Criteria()
                    .andOperator(
                            Criteria
                                    .where("transactionTime")
                                    .gt(fromDate),
                            Criteria
                                    .where("transactionTime")
                                    .lte(toDate)));
        }

        return PageableExecutionUtils.getPage(mongoTemplate.find(query, Transaction.class),
                pageable,
                () -> mongoTemplate.count(query, Transaction.class));

    }

}
