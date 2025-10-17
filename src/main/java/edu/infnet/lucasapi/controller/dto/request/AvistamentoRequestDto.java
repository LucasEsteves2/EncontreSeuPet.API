package edu.infnet.lucasapi.controller.dto.request;

import edu.infnet.lucasapi.domain.model.Avistamento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AvistamentoRequestDto {

    private String descricao;
    private String fotoUrl;

    @NotNull(message = "O ID do pet é obrigatório.")
    private Long petId;

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    @NotNull(message = "A localização é obrigatória.")
    private Long localizacaoId;

    public Avistamento toEntity() {
        return Avistamento.builder()
                .descricao(descricao)
                .fotoUrl(fotoUrl)
                .dataAvistamento(LocalDateTime.now())
                .build();
    }
}
