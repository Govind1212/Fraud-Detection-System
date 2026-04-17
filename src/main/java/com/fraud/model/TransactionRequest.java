package com.fraud.model;

import lombok.Data;

@Data
public class TransactionRequest {
    private String transactionId;
    private String accountId;
    private double amount;
    private String currency;
    private String ipAddress;
    private String merchantCountry;
    private long timestamp;

}
