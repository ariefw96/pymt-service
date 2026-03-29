package com.msig.order.kafka;

import com.msig.order.dto.PaymentRequestDto;
import com.msig.order.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class KafkaConsumer {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PaymentService paymentService;

    @KafkaListener(topics = "payment.sucessfull", groupId = "msig-group")
    public void listenWalletTranfer(String message) {
        System.out.println("Received message[start tranfer]: " + message);
        var val = mapper.readValue(message, PaymentRequestDto.class);

        paymentService.pay(val);
    }

}
