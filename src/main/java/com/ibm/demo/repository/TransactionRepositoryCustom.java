package com.ibm.demo.repository;

import com.ibm.demo.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;


public interface TransactionRepositoryCustom {
    Page<Transaction> findByAccountNumberAndType(String accountNumber, String type,
                                                 LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

}
