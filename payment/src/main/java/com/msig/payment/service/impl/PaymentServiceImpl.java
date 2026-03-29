package com.msig.payment.service.impl;

import com.msig.payment.constant.KafkaTopics;
import com.msig.payment.constant.StatusEnum;
import com.msig.payment.dto.PaymentRequestDto;
import com.msig.payment.entity.OrderEntity;
import com.msig.payment.kafka.KafkaProducerService;
import com.msig.payment.repo.OrderRepo;
import com.msig.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;


@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public Object pay(PaymentRequestDto dto) {
        OrderEntity order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order tidak ditemukan"));

        if(order.getStatus() == StatusEnum.PENDING){
            log.info("Process payment with orderId {}", dto.getOrderId());
            kafkaProducerService.sendMessage(KafkaTopics.PAYMENT_SUCCESSFUL, mapper.writeValueAsString(dto));
            kafkaProducerService.sendMessage(KafkaTopics.NOTIFICATION_PAYMENT_SUCCESSFUL, mapper.writeValueAsString(dto));
        }
        return null;
    }
}
