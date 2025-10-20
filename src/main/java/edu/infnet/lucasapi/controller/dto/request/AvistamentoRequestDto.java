package edu.infnet.lucasapi.controller.dto.request;

import edu.infnet.lucasapi.domain.model.Avistamento;
import jakarta.validation.Valid;
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

    @Valid
    @NotNull(message = "A localização é obrigatória.")
    private LocalizacaoRequestDto localizacao;

    public Avistamento toEntity() {
        return Avistamento.builder()
                .descricao(descricao)
                .fotoUrl(fotoUrl)
                .dataAvistamento(LocalDateTime.now())
                .localizacao(localizacao.toEntity())
                .build();
    }
}
