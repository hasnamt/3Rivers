package com.ibm.demo.service;

import com.ibm.demo.model.Transaction;
import com.ibm.demo.repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TransactionService {

    @Autowired
    private final TransactionsRepository transactionsRepository;

    public Page<Transaction> findTransactions(String accountNumber, String type,
                                              String timeRange, Integer page, Integer pageSize) {
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

        return transactionsRepository.findByAccountNumberAndType(accountNumber,
                type, fromDate, toDate, PageRequest.of(page, pageSize));
    }

}
