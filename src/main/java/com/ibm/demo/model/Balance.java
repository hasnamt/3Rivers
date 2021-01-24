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
@Document(collection = "balance")
public class Balance {
    @Id
    @Field("_id")
    private ObjectId id;
    private String accountNumber;
    private BigDecimal balance;
    private LocalDateTime lastUpdateTimestamp;

}
