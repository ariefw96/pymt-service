package com.msig.payment.repo;

import com.msig.payment.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepo extends JpaRepository<OrderEntity, UUID> {
}
