package com.ibm.demo.controller;

import com.ibm.demo.exception.ResourceNotFoundException;
import com.ibm.demo.model.Balance;
import com.ibm.demo.repository.BalancesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class BalanceController {
    @Autowired
    private final BalancesRepository balancesRepository;

    @GetMapping("/accounts/{accountNumber}/balances")
    public ResponseEntity<Balance> getBalance(@PathVariable String accountNumber) {

        Page<Balance> pageBalance = balancesRepository.findByAccountNumber(accountNumber, PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "lastUpdateTimestamp")));

        if (pageBalance.getTotalElements() == 0) {
            throw new ResourceNotFoundException("balance data not found for the provided accountNumber");
        }

        return ResponseEntity.ok(pageBalance.toList().get(0));
    }

}
