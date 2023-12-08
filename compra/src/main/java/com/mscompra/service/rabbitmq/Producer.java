package com.mscompra.service.rabbitmq;

import com.mscompra.model.Pedido;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class Producer {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public Producer(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    @PostMapping
    public void producer(Pedido pedido) {
        rabbitTemplate.convertAndSend(queue.getName(), pedido);
    }
}
