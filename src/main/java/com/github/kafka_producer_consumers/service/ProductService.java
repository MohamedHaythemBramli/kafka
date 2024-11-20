package com.github.kafka_producer_consumers.service;

import com.github.kafka_producer_consumers.event.CreateProductRequest;

public interface ProductService {
    String createProduct(CreateProductRequest request);
}
