package com.ibm.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "transaction")
public class Transaction {
    @Id
    @Field("_id")
    private ObjectId id;

    private BigDecimal amount;
    private String accountNumber;

    private TransactionType type;
    private LocalDateTime transactionTime;

}
