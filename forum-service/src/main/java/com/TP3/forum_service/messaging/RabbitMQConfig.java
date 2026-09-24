package com.TP3.forum_service.messaging;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.TopicExchange;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "forum.exchange";

    public static final String ROUTING_KEY_BUSCAR = "jogo.buscar";

    public static final String ROUTING_KEY_CONFIRMADO = "jogo.confirmado";

    public static final String ROUTING_KEY_RECUSADO = "jogo.recusado";

    public static final String QUEUE_RESULTADO_JOGO = "forum.resultado-jogo.queue";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue resultadoJogoQueue() {
        return new Queue(QUEUE_RESULTADO_JOGO);
    }

    @Bean
    public Binding bindingResultadoConfirmado(){
        return BindingBuilder.bind(resultadoJogoQueue())
                .to(exchange())
                .with(ROUTING_KEY_CONFIRMADO);
    }

    @Bean
    public Binding bindingResultadoRecusado(){
        return BindingBuilder.bind(resultadoJogoQueue())
                .to(exchange())
                .with(ROUTING_KEY_RECUSADO);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

}
