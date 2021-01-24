package com.ibm.demo.service;

import com.ibm.demo.common.AppConstants;
import com.ibm.demo.model.Transaction;
import com.ibm.demo.repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);

    @Autowired
    private final TransactionsRepository transactionsRepository;


    @KafkaListener(topics = AppConstants.TOPIC_TRANSACTION_CREATE)
    public void consumeTransactions(Transaction transaction) {
        System.out.println("Transaction Message recieved for consumption: " + transaction);
        transactionsRepository.save(transaction);
        logger.info(String.format("Transaction created -> %s", transaction));
    }


}

