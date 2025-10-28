package edu.infnet.lucasapi.api.request;

import edu.infnet.lucasapi.domain.model.Localizacao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LocalizacaoRequest {

    @NotNull(message = "A latitude é obrigatória.")
    private Double latitude;

    @NotNull(message = "A longitude é obrigatória.")
    private Double longitude;

    @Valid
    @NotNull(message = "O endereço é obrigatório.")
    private EnderecoRequest endereco;

    public Localizacao toEntity() {
        return Localizacao.builder()
                .latitude(latitude)
                .longitude(longitude)
                .endereco(endereco.toEntity())
                .build();
    }
}
