package com.msig.order.service.impl;

import com.msig.order.constant.StatusEnum;
import com.msig.order.dto.PaymentRequestDto;
import com.msig.order.entity.OrderEntity;
import com.msig.order.repo.OrderRepo;
import com.msig.order.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public void pay(PaymentRequestDto dto) {

        OrderEntity orderToPaid = orderRepo.findByOrderIdAndStatus(
                dto.getOrderId(),
                StatusEnum.PENDING
        ).orElse(null);

        if(orderToPaid == null){
            log.info("Order {} already paid ", dto.getOrderId());
            return;
        }

        log.info("Order {} paid with total price {}", dto.getOrderId(), dto.getTotalPrice());

        orderToPaid.setStatus(StatusEnum.PAID);
        orderRepo.save(orderToPaid);

    }
}
