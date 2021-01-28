package com.ibm.demo;

import com.ibm.demo.model.Transaction;
import com.ibm.demo.model.TransactionType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootApplication
@RequiredArgsConstructor
public class AggregateServiceApplication {


    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final Logger logger =
            LoggerFactory.getLogger(AggregateServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AggregateServiceApplication.class, args);

    }

    @Component
    public class TransactionEventSource implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            Transaction transaction = new Transaction();
            transaction.setAccountNumber("123");
            transaction.setAmount(new BigDecimal("123"));
            transaction.setType(TransactionType.WITHDRAW);
            transaction.setTransactionTime(LocalDateTime.now());
            saveCreateUserLog(transaction);
        }
    }


    public void saveCreateUserLog(Transaction transaction) {
        logger.info(String.format("User created -> %s", transaction.getTransactionTime()));

        //   this.kafkaTemplate.send(AppConstants.TOPIC_TRANSACTION_CREATE, transaction);
    }


}
