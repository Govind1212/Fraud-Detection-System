package com.fraud.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionScored {
    private String transactionId;
    private int fraudScore;
    private String status;
}
