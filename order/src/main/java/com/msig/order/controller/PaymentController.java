package com.msig.order.controller;

import com.msig.order.dto.PaymentRequestDto;
import com.msig.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/pay")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("")
    public ResponseEntity<Object> createPayment(
            @RequestBody PaymentRequestDto dto
    ) {
        paymentService.pay(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
