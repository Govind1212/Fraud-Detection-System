package com.fraud.Controller;

import com.fraud.Service.KafkaProducerService;
import com.fraud.Service.TransactionresultManager;
import com.fraud.model.TransactionRequest;
import com.fraud.model.TransactionScored;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final KafkaProducerService producerService;
    private final TransactionresultManager resultManager;

    @PostMapping
    public CompletableFuture<ResponseEntity<TransactionScored>>ingestTransaction(@RequestBody TransactionRequest request)
    {
        if(request.getTransactionId()==null)
        {
            request.setTransactionId(UUID.randomUUID().toString());
        }

        //Create a pending request future
        CompletableFuture<TransactionScored> future = resultManager.createPendingRequest(request.getTransactionId());
        //Publis to kafka
        producerService.publishRawTransaction(request);
        return future
                .orTimeout(5, TimeUnit.SECONDS)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex->ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build());
    }
}
