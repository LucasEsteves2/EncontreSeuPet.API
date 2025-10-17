package edu.infnet.lucasapi.controller.dto.request;

import edu.infnet.lucasapi.domain.model.Notificacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificacaoRequestDto {

    @NotBlank(message = "A mensagem da notificação é obrigatória.")
    private String mensagem;

    @NotNull(message = "O ID do usuário destinatário é obrigatório.")
    private Long usuarioDestinatarioId;

    @NotNull(message = "O ID do avistamento é obrigatório.")
    private Long avistamentoId;

    private Boolean lida = false;

    public Notificacao toEntity() {
        return Notificacao.builder()
                .mensagem(mensagem)
                .lida(lida != null ? lida : false)
                .dataEnvio(LocalDateTime.now())
                .build();
    }
}
