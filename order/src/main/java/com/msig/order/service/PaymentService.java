package com.msig.order.service;

import com.msig.order.dto.PaymentRequestDto;

public interface PaymentService {

    public void pay(PaymentRequestDto dto);

}
