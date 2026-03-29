package com.msig.notification.service;

import com.msig.notification.dto.PaymentRequestDto;
import com.msig.notification.entity.OrderEntity;
import com.msig.notification.repo.OrderRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class NotificationService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${mail-to:bronyazaychik96@gmail.com}")
    private String mailTo;

    @Transactional(readOnly = true)
    public void sendOrderConfirmation(PaymentRequestDto pymt) {
        try {
            OrderEntity order = orderRepo.findById(pymt.getOrderId())
                    .orElse(null);
            if(order == null){
                return;
            }
            Context context = new Context();
            context.setVariable("orderId", order.getOrderId());
            context.setVariable("details", order.getOrderDetails());
            context.setVariable("totalPrice", order.getTotalPrice());

            String htmlContent = templateEngine.process("order-email", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setTo(mailTo);
            helper.setSubject("Konfirmasi Pesanan - " + order.getOrderId());
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Gagal mengirim email", e);
        }
    }
}