package com.workercompras.service.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workercompras.model.Cartao;
import com.workercompras.model.Pedido;
import com.workercompras.service.CartaoService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Service
public class PedidoProducer {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;
    private final ObjectMapper mapper;
    private final CartaoService cartaoService;

    public PedidoProducer(RabbitTemplate rabbitTemplate, Queue queue, ObjectMapper mapper, CartaoService cartaoService) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.mapper = mapper;
        this.cartaoService = cartaoService;
    }

    @SneakyThrows
    @PostMapping
    public void enviarPedido(Pedido pedido) {
        pedido.setCartao(Cartao.builder()
                .numero(cartaoService.gerarCartao())
                .limiteDisponivel(cartaoService.gerarLimite())
                .build());
        rabbitTemplate.convertAndSend(queue.getName(), mapper.writeValueAsString(pedido));
        log.info("Pedido montado com sucesso em Worker Compras - PedidoProducer: {}", mapper.writeValueAsString(pedido));
    }
}
