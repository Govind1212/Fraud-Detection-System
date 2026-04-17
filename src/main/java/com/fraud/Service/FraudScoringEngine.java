package com.fraud.Service;

import com.fraud.model.TransactionRequest;
import org.rocksdb.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudScoringEngine {
    @Value("#{'${com.enterprise.fraud.scoring.system.high.risk.countries}'.split(',')}")
    private List<String> HIGH_RISK_COUNTRIES;

    @Value(("#{'${com.enterprise.fraud.scoring.system.threshold.amount}"))
    private String thresholdAmount;

    public int calculateScore(TransactionRequest txn)
    {
        int score = 0;

        if(txn.getAmount() > Integer.valueOf(thresholdAmount))
        {
            score += 5;
        }
        else if(txn.getAmount()>5000)
        {
            score += 3;
        }

        if(HIGH_RISK_COUNTRIES.contains(txn.getMerchantCountry().toLowerCase()))
        {
            score += 4;
        }

        if(txn.getIpAddress()==null || txn.getIpAddress().isEmpty())
        {
            score += 2;
        }

        return Math.min(Math.max(score,0),10);
    }
}
