package com.msig.payment.controller;

import com.msig.payment.dto.PaymentRequestDto;
import com.msig.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/webhook")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/process")
    public ResponseEntity<Object> doProcessWehbook(
            @RequestBody PaymentRequestDto paymentRequestDto
    ) {

        return new ResponseEntity<>(paymentService.pay(paymentRequestDto), HttpStatus.OK);
    }

}
