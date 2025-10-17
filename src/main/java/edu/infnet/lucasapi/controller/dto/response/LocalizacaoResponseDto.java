package edu.infnet.lucasapi.controller.dto.response;

import edu.infnet.lucasapi.domain.model.Localizacao;
import edu.infnet.lucasapi.domain.enums.TipoLocalizacao;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocalizacaoResponseDto {

    private Long id;
    private Double latitude;
    private Double longitude;
    private TipoLocalizacao tipo;
    private EnderecoResponseDto endereco;

    public static LocalizacaoResponseDto fromEntity(Localizacao l) {
        if (l == null) return null;

        return LocalizacaoResponseDto.builder()
                .id(l.getId())
                .latitude(l.getLatitude())
                .longitude(l.getLongitude())
                .tipo(l.getTipo())
                .endereco(l.getEndereco() != null
                        ? EnderecoResponseDto.fromEntity(l.getEndereco())
                        : null)
                .build();
    }
}
