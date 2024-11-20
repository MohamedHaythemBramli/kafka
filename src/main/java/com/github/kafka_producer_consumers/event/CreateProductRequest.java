package com.github.kafka_producer_consumers.event;

import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class CreateProductRequest {

    private String title;
    private BigDecimal price;
    private Integer quantity;
}
