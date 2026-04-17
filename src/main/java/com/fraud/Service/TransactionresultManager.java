package com.fraud.Service;

import com.fraud.model.TransactionScored;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionresultManager {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;
    private final Map<String, CompletableFuture<TransactionScored>> pendingRequests = new ConcurrentHashMap<>();
    private final Map<String, TransactionScored> completedResults = new ConcurrentHashMap<>();

    public CompletableFuture<TransactionScored> createPendingRequest(String transactionId)
    {
        CompletableFuture<TransactionScored>future = new CompletableFuture<>();
        pendingRequests.put(transactionId, future);
        return future;
    }

    public void completeRequest(TransactionScored result)
    {
        completedResults.put(result.getTransactionId(), result);
        CompletableFuture<TransactionScored> future = pendingRequests.remove(result.getTransactionId());
        if(future != null)
        {
            future.complete(result);
        }
    }

    public TransactionScored getCompletedResult(String transactionID)
    {
        return completedResults.get(transactionID);
    }

}
