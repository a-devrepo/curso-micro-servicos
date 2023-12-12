package com.worker.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worker.model.Pedido;
import com.worker.service.ValidadorService;
import com.worker.service.exceptions.LimiteIndisponivelException;
import com.worker.service.exceptions.SaldoInsuficienteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class Consumer {
    private final ObjectMapper mapper;
    private ValidadorService validadorService;

    public Consumer(ObjectMapper mapper, ValidadorService validadorService) {
        this.mapper = mapper;
        this.validadorService = validadorService;
    }

    @RabbitListener(queues = {"${queue.name}"})
    public void consumer(@Payload Message message) throws IOException {
        var pedido = mapper.readValue(message.getBody(), Pedido.class);
        log.info("Pedido recebido no Validador: {}", pedido);
        try {
            validadorService.validarPedido(pedido);
        } catch (LimiteIndisponivelException | SaldoInsuficienteException ex) {

        }
    }
}