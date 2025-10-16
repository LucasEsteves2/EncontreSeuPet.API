package edu.infnet.lucasapi.service;

import edu.infnet.lucasapi.domain.model.Notificacao;
import edu.infnet.lucasapi.repository.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacaoService extends BaseCrudService<Notificacao, Long> {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        super(notificacaoRepository);
        this.notificacaoRepository = notificacaoRepository;
    }

    public List<Notificacao> buscarPorUsuario(Long usuarioId) {
        return notificacaoRepository.findByUsuarioDestinatario_Id(usuarioId);
    }
}
