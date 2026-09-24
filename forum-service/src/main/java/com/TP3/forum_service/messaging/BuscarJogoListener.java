package com.TP3.forum_service.messaging;

import com.TP3.forum_service.entities.Topico;
import com.TP3.forum_service.entities.TopicoStatus;
import com.TP3.forum_service.service.ForumService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BuscarJogoListener {

    private ForumService forumService;

    public BuscarJogoListener(ForumService forumService) {
        this.forumService = forumService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_RESULTADO_JOGO)
    public void atualizarResultadoJogo(ResultadoCommand resultado){
        Topico topico = forumService.acharTopico(resultado.getIdTopico());

        if (resultado.getMotivo() == null){
            topico.setTitutloJogo(resultado.getTituloJogo());
            topico.setIdJogo(resultado.getIdJogo());
            topico.setStatus(TopicoStatus.ACEITO);
        } else {
            topico.setMotivoErro(resultado.getMotivo());
            topico.setStatus(TopicoStatus.RECUSADO);
        }

        forumService.atualizarTopico(topico.getId(), topico);
    }


}
