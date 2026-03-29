package com.msig.order.service;

import com.msig.order.dto.OrderRequestDto;

import java.util.UUID;

public interface OrderService {

    UUID saveOrder(OrderRequestDto dto);
}
