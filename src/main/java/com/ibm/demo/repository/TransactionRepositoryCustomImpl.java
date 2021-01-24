package com.ibm.demo.repository;

import com.ibm.demo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Transaction> findByAccountNumberAndType(String accountNumber, String type, String timeRange, Pageable pageable) {

        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;
        if (timeRange != null) {
            switch (timeRange) {
                case "Today":
                    fromDate = LocalDate.now().atStartOfDay();
                    toDate = LocalDateTime.now();
                    break;
                case "Last 7 Days":
                    fromDate = LocalDate.now().minusDays(7).atStartOfDay();
                    toDate = LocalDateTime.now();
                    break;
                case "Last Month":
                    fromDate = LocalDate.now().minusMonths(1).atStartOfDay();
                    toDate = LocalDateTime.now();
                    break;
            }

            if (timeRange.contains("~")) {
                String[] dates = timeRange.split("~");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                fromDate = LocalDate.parse(dates[0], formatter).atStartOfDay();
                toDate = LocalDate.parse(dates[1], formatter).atStartOfDay();
            }
        }

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
