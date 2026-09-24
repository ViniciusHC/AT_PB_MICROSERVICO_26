package com.TP2.PJB.messaging;
import com.TP2.PJB.model.Boardgame;
import com.TP2.PJB.service.BoardGameService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ForumListenerResultadoPublisher {

    private final BoardGameService boardGameService;
    private final RabbitTemplate rabbitTemplate;

    public ForumListenerResultadoPublisher(BoardGameService boardGameService, RabbitTemplate rabbitTemplate) {
        this.boardGameService = boardGameService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_BUSCAR_JOGO)
    public void buscarJogo(BuscarJogoCommand jogoCommand) {
        try{
            Boardgame jogoAchado = boardGameService.buscarJogoPorTitulo(jogoCommand.getTituloJogo());
            ResultadoCommand resultadoCommand = new ResultadoCommand(jogoAchado.getId(), jogoCommand.getIdTopico(), jogoAchado.getNome(), null);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_CONFIRMADO,
                    resultadoCommand
            );
        }catch (EntityNotFoundException e){
            ResultadoCommand resultadoCommand = new ResultadoCommand(null, jogoCommand.getIdTopico(), null, e.getMessage());
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_RECUSADO,
                    resultadoCommand
            );
        }

    }

}
