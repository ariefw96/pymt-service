package com.msig.order.repo;

import com.msig.order.entity.OrderDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDetailsRepo extends JpaRepository<OrderDetailsEntity, UUID> {
}
