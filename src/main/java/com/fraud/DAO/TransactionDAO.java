package com.fraud.DAO;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionDAO {
    private String enterpriseTransactionId;
    private String enterpriseId;
    private String sourceServer;
    private String transactionId;
    private BigDecimal amount;
    private String currency;
    private String pii;
    private Instant receivedAt;

    public TransactionDAO() {
    }

    public TransactionDAO(
            String enterpriseTransactionId,
            String enterpriseId,
            String sourceServer,
            String transactionId,
            BigDecimal amount,
            String currency,
            String pii,
            Instant receivedAt) {
        this.enterpriseTransactionId = enterpriseTransactionId;
        this.enterpriseId = enterpriseId;
        this.sourceServer = sourceServer;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.pii = pii;
        this.receivedAt = receivedAt;
    }

    public String getEnterpriseTransactionId() {
        return enterpriseTransactionId;
    }

    public void setEnterpriseTransactionId(String enterpriseTransactionId) {
        this.enterpriseTransactionId = enterpriseTransactionId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getSourceServer() {
        return sourceServer;
    }

    public void setSourceServer(String sourceServer) {
        this.sourceServer = sourceServer;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPii() {
        return pii;
    }

    public void setPii(String pii) {
        this.pii = pii;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }
}
