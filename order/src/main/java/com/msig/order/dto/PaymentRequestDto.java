package com.msig.order.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequestDto {

    private UUID orderId;
    private BigDecimal totalPrice;

}
