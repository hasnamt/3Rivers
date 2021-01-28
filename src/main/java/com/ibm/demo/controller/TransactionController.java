package com.ibm.demo.controller;

import com.ibm.demo.model.Transaction;
import com.ibm.demo.model.TransactionResponse;
import com.ibm.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private final TransactionService service;

    @GetMapping("/accounts/{accountNumber}/transactions")
    public ResponseEntity<TransactionResponse> getTransactions(@PathVariable String accountNumber,
                                                               @RequestParam(value = "time_range", required = false) String timeRange,
                                                               @RequestParam(value = "type", required = false) String type,
                                                               @RequestParam(value = "page_size", required = false, defaultValue = "20") Integer pageSize,
                                                               @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {


        Page<Transaction> pages = service.findTransactions(accountNumber, type, timeRange, page, pageSize);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTotalPages(pages.getTotalPages());
        transactionResponse.setTotalElements(pages.getTotalElements());
        transactionResponse.setTransactions(pages.toList());

        return ResponseEntity.ok(transactionResponse);
    }

}
