package com.msig.order.controller;

import com.msig.order.dto.OrderRequestDto;
import com.msig.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequestDto dto) {

        UUID orderId =  orderService.saveOrder(dto);
        Map<String, Object> response = Map.of("orderId", orderId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
