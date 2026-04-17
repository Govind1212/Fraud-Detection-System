package com.fraud.Service;

import com.fraud.model.TransactionRequest;
import com.fraud.model.TransactionScored;
import lombok.RequiredArgsConstructor;
import org.rocksdb.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private  final KafkaTemplate<String, Object>kafkaTemplate;

    @Value("${kafka.topic.inboun}")
    private String inboundTopic;

    @Value(("${kafka.topic.outbound}"))
    private String outboundTopic;

    public void publishRawTransaction(TransactionRequest request)
    {
        kafkaTemplate.send(inboundTopic, request.getTransactionId(), request);
    }

    public void publishScoredTransaction(TransactionScored scoredTxn)
    {
        kafkaTemplate.send(outboundTopic, scoredTxn.getTransactionId(), scoredTxn);
    }
}
