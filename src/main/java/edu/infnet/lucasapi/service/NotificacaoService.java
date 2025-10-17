package edu.infnet.lucasapi.service;

import edu.infnet.lucasapi.domain.model.Avistamento;
import edu.infnet.lucasapi.domain.model.Notificacao;
import edu.infnet.lucasapi.domain.model.Usuario;
import edu.infnet.lucasapi.repository.NotificacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificacaoService extends BaseCrudService<Notificacao, Long> {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        super(notificacaoRepository);
        this.notificacaoRepository = notificacaoRepository;
    }

    public void criarNotificacao(Usuario destinatario, String mensagem, Avistamento avistamento) {

        if (destinatario == null) return;

        var notificacao = Notificacao.builder()
                .usuarioDestinatario(destinatario)
                .mensagem(mensagem)
                .dataEnvio(LocalDateTime.now())
                .lida(false)
                .avistamento(avistamento)
                .build();

        criar(notificacao);
    }


    public Page<Notificacao> buscarComFiltros(Pageable pageable, Long usuarioId, Long avistamentoId, Boolean lida) {
        var filtros = criarFiltros(usuarioId, avistamentoId, lida);
        return notificacaoRepository.findAll(filtros, pageable);
    }

    private Specification<Notificacao> criarFiltros(Long usuarioId, Long avistamentoId, Boolean lida) {
        Specification<Notificacao> filtros = (root, query, cb) -> cb.conjunction();

        if (usuarioId != null)
            filtros = filtros.and((root, query, cb) ->
                    cb.equal(root.get("usuarioDestinatario").get("id"), usuarioId));

        if (avistamentoId != null)
            filtros = filtros.and((root, query, cb) ->
                    cb.equal(root.get("avistamento").get("id"), avistamentoId));

        if (lida != null)
            filtros = filtros.and((root, query, cb) ->
                    cb.equal(root.get("lida"), lida));

        return filtros;
    }
}
