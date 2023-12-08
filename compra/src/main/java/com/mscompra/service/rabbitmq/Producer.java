package com.mscompra.service.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscompra.model.Pedido;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class Producer {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    private final ObjectMapper mapper;

    public Producer(RabbitTemplate rabbitTemplate, Queue queue, ObjectMapper mapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.mapper = mapper;
    }

    @SneakyThrows
    @PostMapping
    public void producer(Pedido pedido) {
        rabbitTemplate.convertAndSend(queue.getName(), mapper.writeValueAsString(pedido));
    }
}
