package edu.infnet.lucasapi.api.response;

import edu.infnet.lucasapi.domain.model.Avistamento;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AvistamentoResponse {

    private Long id;
    private Long petId;
    private Long usuarioId;
    private String descricao;
    private String fotoUrl;
    private LocalDateTime dataAvistamento;

    private LocalizacaoResponse localizacao;

    public static AvistamentoResponse fromEntity(Avistamento a) {
        if (a == null) return null;

        return AvistamentoResponse.builder()
                .id(a.getId())
                .descricao(a.getDescricao())
                .fotoUrl(a.getFotoUrl())
                .dataAvistamento(a.getDataAvistamento())
                .petId(a.getPet() != null ? a.getPet().getId() : null)
                .usuarioId(a.getUsuario() != null ? a.getUsuario().getId() : null)
                .localizacao(LocalizacaoResponse.fromEntity(a.getLocalizacao()))
                .build();
    }

    public static List<AvistamentoResponse> fromEntities(List<Avistamento> avistamentos) {
        if (avistamentos == null) return null;

        return avistamentos.stream()
                .map(AvistamentoResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
