package com.fraud.DAO;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class TransactionRepository {
    private final List<TransactionDAO> transactions = new CopyOnWriteArrayList<>();

    public TransactionDAO save(TransactionDAO transactionDAO) {
        transactions.add(transactionDAO);
        return transactionDAO;
    }

    public List<TransactionDAO> findAll() {
        return new ArrayList<>(transactions);
    }
}
