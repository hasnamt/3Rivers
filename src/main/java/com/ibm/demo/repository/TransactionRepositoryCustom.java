package com.ibm.demo.repository;

import com.ibm.demo.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface TransactionRepositoryCustom {
    Page<Transaction> findByAccountNumberAndType(String accountNumber, String type, String timeRange, Pageable pageable);

}
