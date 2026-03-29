package com.msig.notification.kafka;

import com.msig.notification.dto.PaymentRequestDto;
import com.msig.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class KafkaConsumer {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "notification.payment.successful", groupId = "msig-group")
    public void listenWalletTranfer(String message) {
        System.out.println("Received message[start notification send]: " + message);
        var val = mapper.readValue(message, PaymentRequestDto.class);

        notificationService.sendOrderConfirmation(val);
    }

}
