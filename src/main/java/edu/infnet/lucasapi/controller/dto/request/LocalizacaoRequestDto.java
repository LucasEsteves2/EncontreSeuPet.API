package edu.infnet.lucasapi.controller.dto.request;

import edu.infnet.lucasapi.domain.enums.TipoLocalizacao;
import edu.infnet.lucasapi.domain.model.Localizacao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocalizacaoRequestDto {

    @NotNull(message = "A latitude é obrigatória.")
    private Double latitude;

    @NotNull(message = "A longitude é obrigatória.")
    private Double longitude;

    @Valid
    @NotNull(message = "O endereço é obrigatório.")
    private EnderecoRequestDto endereco;

    public Localizacao toEntity() {
        return Localizacao.builder()
                .latitude(latitude)
                .longitude(longitude)
                .endereco(endereco.toEntity())
                .build();
    }
}
