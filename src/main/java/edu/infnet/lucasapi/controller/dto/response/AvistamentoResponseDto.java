package edu.infnet.lucasapi.controller.dto.response;

import edu.infnet.lucasapi.domain.model.Avistamento;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class AvistamentoResponseDto {

    private Long id;
    private Long petId;
    private Long usuarioId;
    private String descricao;
    private String fotoUrl;
    private LocalDateTime dataAvistamento;

    private LocalizacaoResponseDto localizacao;

    public static AvistamentoResponseDto fromEntity(Avistamento a) {
        if (a == null) return null;

        return AvistamentoResponseDto.builder()
                .id(a.getId())
                .descricao(a.getDescricao())
                .fotoUrl(a.getFotoUrl())
                .dataAvistamento(a.getDataAvistamento())
                .petId(a.getPet() != null ? a.getPet().getId() : null)
                .usuarioId(a.getUsuario() != null ? a.getUsuario().getId() : null)
                .localizacao(LocalizacaoResponseDto.fromEntity(a.getLocalizacao()))
                .build();
    }

    public static List<AvistamentoResponseDto> fromEntities(List<Avistamento> avistamentos) {
        if (avistamentos == null) return null;

        return avistamentos.stream()
                .map(AvistamentoResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
