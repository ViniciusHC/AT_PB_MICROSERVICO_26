package com.TP2.PJB.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "forum.exchange";

    public static final String ROUTING_KEY_BUSCAR = "jogo.buscar";

    public static final String ROUTING_KEY_CONFIRMADO = "jogo.confirmado";

    public static final String ROUTING_KEY_RECUSADO = "jogo.recusado";

    public static final String QUEUE_BUSCAR_JOGO = "forum.buscar-jogo.queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue bucarJogoQueue() {
        return new Queue(QUEUE_BUSCAR_JOGO);
    }

    @Bean
    public Binding bindingBuscarJogo(){
        return BindingBuilder.bind(bucarJogoQueue())
                .to(exchange())
                .with(ROUTING_KEY_BUSCAR);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

}
