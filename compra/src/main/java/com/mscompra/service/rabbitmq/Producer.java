package com.mscompra.service.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class Producer {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public Producer(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    @PostMapping
    public void producer(@RequestBody String payload) {
        rabbitTemplate.convertAndSend(queue.getName(), payload);
    }
}
