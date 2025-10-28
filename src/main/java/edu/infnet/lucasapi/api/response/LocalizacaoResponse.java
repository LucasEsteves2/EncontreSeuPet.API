package edu.infnet.lucasapi.api.response;

import edu.infnet.lucasapi.domain.model.Localizacao;
import edu.infnet.lucasapi.domain.enums.TipoLocalizacao;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocalizacaoResponse {

    private Long id;
    private Double latitude;
    private Double longitude;
    private TipoLocalizacao tipo;
    private EnderecoResponse endereco;

    public static LocalizacaoResponse fromEntity(Localizacao l) {
        if (l == null) return null;

        return LocalizacaoResponse.builder()
                .id(l.getId())
                .latitude(l.getLatitude())
                .longitude(l.getLongitude())
                .tipo(l.getTipo())
                .endereco(l.getEndereco() != null
                        ? EnderecoResponse.fromEntity(l.getEndereco())
                        : null)
                .build();
    }
}
