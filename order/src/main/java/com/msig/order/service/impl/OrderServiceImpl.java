package com.msig.order.service.impl;

import com.msig.order.constant.StatusEnum;
import com.msig.order.dto.OrderRequestDto;
import com.msig.order.entity.OrderDetailsEntity;
import com.msig.order.entity.OrderEntity;
import com.msig.order.repo.OrderRepo;
import com.msig.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Override
    public UUID saveOrder(OrderRequestDto dto) {

        List<OrderDetailsEntity> orderDetails = dto.getData().stream()
                .map(od -> {
                    OrderDetailsEntity ods = new OrderDetailsEntity();
                    ods.setProductName(od.getProductName());
                    ods.setQuantity(od.getQuantity());
                    ods.setProductPrice(od.getPrice());
                    return ods;
                }).toList();

        BigDecimal totalPrice = orderDetails.stream()
                .map(od -> od.getProductPrice().multiply(BigDecimal.valueOf(od.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity order = new OrderEntity();
        order.setTotalPrice(totalPrice);
        order.setStatus(StatusEnum.PENDING);
        order.setOrderDetails(orderDetails);

        orderRepo.save(order);

        return order.getOrderId();
    }
}
