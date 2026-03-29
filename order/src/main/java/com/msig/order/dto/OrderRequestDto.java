package com.msig.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {

    List<OrderDetailRequestDto> data;
}
