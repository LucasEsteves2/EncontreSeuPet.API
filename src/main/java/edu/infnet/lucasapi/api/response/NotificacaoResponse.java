package edu.infnet.lucasapi.api.response;

import edu.infnet.lucasapi.domain.model.Notificacao;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class NotificacaoResponse {

    private Long id;
    private String mensagem;
    private Boolean lida;
    private LocalDateTime dataEnvio;
    private String usuarioNome;
    private Long avistamentoId;
    private String petNome;
    private String usuarioAvistamento;

    public static NotificacaoResponse fromEntity(Notificacao n) {
        return NotificacaoResponse.builder()
                .id(n.getId())
                .mensagem(n.getMensagem())
                .lida(n.getLida())
                .dataEnvio(n.getDataEnvio())
                .usuarioNome(n.getUsuarioDestinatario() != null ? n.getUsuarioDestinatario().getNome() : null)
                .avistamentoId(n.getAvistamento() != null ? n.getAvistamento().getId() : null)
                .petNome(n.getAvistamento() != null && n.getAvistamento().getPet() != null
                        ? n.getAvistamento().getPet().getNome() : null)
                .usuarioAvistamento(n.getAvistamento() != null && n.getAvistamento().getUsuario() != null
                        ? n.getAvistamento().getUsuario().getNome() : null)
                .build();
    }

    public static List<NotificacaoResponse> fromEntities(List<Notificacao> notificacoes) {
        return notificacoes.stream()
                .map(NotificacaoResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
