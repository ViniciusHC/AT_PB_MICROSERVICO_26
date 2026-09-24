package com.TP3.forum_service.service;

import com.TP3.forum_service.DTO.TopicoDTO;
import com.TP3.forum_service.entities.Topico;
import com.TP3.forum_service.entities.TopicoStatus;
import com.TP3.forum_service.messaging.BuscarJogoCommand;
import com.TP3.forum_service.messaging.ForumPublisher;
import com.TP3.forum_service.repository.ForumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumService {

    private final ForumRepository forumRepository;
    private final ForumPublisher forumPublisher;

    public ForumService(ForumRepository forumRepository, ForumPublisher forumPublisher) {
        this.forumRepository = forumRepository;
        this.forumPublisher = forumPublisher;
    }

    public List<Topico> listarTopicos(){
        return forumRepository.findAll();
    }

    public Topico listarTopico(Long id){
        return forumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
    }

    public Topico adicionarTopico(TopicoDTO topicoDTO){
        Topico topico = new Topico();
        topico.setIdUsuario(topicoDTO.getIdUsuario());
        topico.setTitulo(topicoDTO.getTitulo());
        topico.setTitutloJogo(topicoDTO.getTitutloJogo());
        topico.setTexto(topicoDTO.getTexto());
        topico.setDataCriacao(LocalDateTime.now());
        topico.setStatus(TopicoStatus.PENDENTE);
        Topico topicoSalvo = forumRepository.save(topico);

        //RabbitMQ - agora fazer o listener no boardgame
        forumPublisher.publicarBuscarJogo(
                new BuscarJogoCommand(topico.getId(), topico.getTitutloJogo())
        );

        return topicoSalvo;
    }

    public Topico alterarTopico(Long id, TopicoDTO topicoAlterado){
        Topico topico = forumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
        topico.setTitulo(topicoAlterado.getTitulo());
        topico.setTexto(topicoAlterado.getTexto());
        return forumRepository.save(topico);
    }

    public Topico acharTopico(Long id){
        return forumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
    }

    public void removerTopico(Long id) {
        Topico topico = forumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
        forumRepository.delete(topico);
    }

    public Topico atualizarTopico(Long id, Topico topicoAtualizado){
        Topico topico = forumRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));
        topico.setStatus(topicoAtualizado.getStatus());
        topico.setIdJogo(topicoAtualizado.getIdJogo());
        topico.setTitutloJogo(topicoAtualizado.getTitutloJogo());
        topico.setMotivoErro(topicoAtualizado.getMotivoErro());
        return forumRepository.save(topico);
    }


}
