package com.msig.payment.service;

import com.msig.payment.dto.PaymentRequestDto;
import com.msig.payment.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface PaymentService {

    public Object pay(PaymentRequestDto dto);

}
