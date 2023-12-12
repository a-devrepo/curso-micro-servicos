package com.worker.service;

import com.worker.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void notificarCliente(Pedido pedido) {
        var msg = new SimpleMailMessage();
        msg.setTo(pedido.getEmail());
        msg.setSubject("Compra confirmada");
        msg.setText("Sua compra foi aprovada! Em breve enviaremos o código de rastreio");
        javaMailSender.send(msg);
        log.info("Cliente notificado da compra aprovada com sucesso.");
    }

}
