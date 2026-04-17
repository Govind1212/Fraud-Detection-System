package com.fraud.Service;

import com.fraud.model.TransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FraudScoringEngineTest {

    private FraudScoringEngine fraudScoringEngine;

    @BeforeEach
    void setUp() {
        fraudScoringEngine = new FraudScoringEngine();
        ReflectionTestUtils.setField(
                fraudScoringEngine,
                "HIGH_RISK_COUNTRIES",
                List.of("ng", "ru", "kp")
        );
        ReflectionTestUtils.setField(fraudScoringEngine, "thresholdAmount", "10000");
    }

    @Test
    void calculateScore_appliesAllRiskSignals_andCapsAtTen() {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(15000);
        request.setMerchantCountry("NG");
        request.setIpAddress("");

        int score = fraudScoringEngine.calculateScore(request);

        assertEquals(10, score);
    }

    @Test
    void calculateScore_usesMidAmountBranchWhenBelowThreshold() {
        TransactionRequest request = new TransactionRequest();
        request.setAmount(7000);
        request.setMerchantCountry("us");
        request.setIpAddress("192.168.0.10");

        int score = fraudScoringEngine.calculateScore(request);

        assertEquals(3, score);
    }
}
