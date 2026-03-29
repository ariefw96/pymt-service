package com.msig.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetailRequestDto {

    private String productName;
    private BigDecimal price;
    private Integer quantity;

}
