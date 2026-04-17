package com.fraud.Service;

import com.fraud.model.TransactionRequest;
import com.fraud.model.TransactionScored;
import com.fraud.utils.TransactionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final FraudScoringEngine scoringEngine;
    private final KafkaProducerService producerService;
    private final TransactionresultManager resultManager;

    @KafkaListener(topics="${kafka.topic.inboun}", groupId = "fraud-processing-group")
    public void consumeTransaction(TransactionRequest request)
    {
        log.debug("Consumed transaction ID: {}", request.getTransactionId());

        int score = scoringEngine.calculateScore(request);

        String status = score>=7 ? TransactionStatus.REJECTED.name() : (score>=4 ? TransactionStatus.FLAGGED_FOR_MANUAL_REVIEW.name() : TransactionStatus.APPROVED.name());

        //Send score directly back to the http thread that is waiting
        TransactionScored result = new TransactionScored(request.getTransactionId(),score,status);

        resultManager.completeRequest(result);
        log.info("Transaction {} scored {}", request.getTransactionId(), score);
        //publish to the outbound topic for other systems for data analysis
        producerService.publishScoredTransaction(result);
    }
}
