package com.msig.notification.repo;

import com.msig.notification.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepo extends JpaRepository<OrderEntity, UUID> {

}
