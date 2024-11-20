package com.github.kafka_producer_consumers.controller;

import com.github.kafka_producer_consumers.event.CreateProductRequest;
import com.github.kafka_producer_consumers.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRequest request){
        String productId = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
