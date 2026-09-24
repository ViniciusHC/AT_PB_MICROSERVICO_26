package com.TP3.forum_service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ForumPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ForumPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarBuscarJogo (BuscarJogoCommand buscarJogoCommand){
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_BUSCAR,
                buscarJogoCommand
        );
    }
}
