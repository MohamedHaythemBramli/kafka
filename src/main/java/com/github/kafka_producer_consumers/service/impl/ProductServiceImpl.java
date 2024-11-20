package com.github.kafka_producer_consumers.service.impl;

import com.github.core_module.CreateProductEvent;
import com.github.kafka_producer_consumers.event.CreateProductRequest;
import com.github.kafka_producer_consumers.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final KafkaTemplate<String,CreateProductEvent> kafkaTemplate;
    @Override
    public String createProduct(CreateProductRequest request) {
        String productId = UUID.randomUUID().toString();
        CreateProductEvent createProductEvent = CreateProductEvent.builder()
                .productId(productId)
                .title(request.getTitle())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();

        ProducerRecord<String,CreateProductEvent> producerRecord = new ProducerRecord<>("product-created-topic-events",
                productId,
                createProductEvent);
        producerRecord.headers().add("messageId",UUID.randomUUID().toString().getBytes());

        CompletableFuture<SendResult<String, CreateProductEvent>> send =
                kafkaTemplate.send(producerRecord);
        send.whenComplete((result,exception)->{
            if (exception!=null){
                log.error("***** Error sending product event: {}", exception.getMessage());
            }
            else {
                log.info("Partition:" +result.getRecordMetadata().partition());
                log.info("Topic:" +result.getRecordMetadata().topic());
                log.info("Offset:" +result.getRecordMetadata().offset());
                log.info("Record"+result.getProducerRecord());
            }
        });
        //send.join(); this make the code synchronous
        log.info("***** Returning product Id");
        return productId;
    }
}
