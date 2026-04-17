package com.fraud.Controller;

import com.fraud.Service.KafkaProducerService;
import com.fraud.Service.TransactionresultManager;
import com.fraud.model.TransactionRequest;
import com.fraud.model.TransactionScored;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
                .orTimeout(3, TimeUnit.SECONDS)
                .thenApply(ResponseEntity::ok)
                .exceptionally(ex->{
                    if(ex.getCause() instanceof TimeoutException)
                    {
                        TransactionScored pendingResponse = new TransactionScored(request.getTransactionId(),0,"PENDING");
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pendingResponse);
                    }
                   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    //Endpoint for a enterprises to poll if they received a pending status
    @GetMapping("/{transactionID}")
    public ResponseEntity<?> getTransactionStatus(@PathVariable String transactionID)
    {
        TransactionScored result = resultManager.getCompletedResult(transactionID);

        if(result!=null)
        {
            return ResponseEntity.ok(result);
        }
        else {
            TransactionScored pendingresponse = new TransactionScored(transactionID,0,"PENDING");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(pendingresponse);
        }
    }
}
