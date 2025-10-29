package edu.infnet.lucasapi.api.request;

import edu.infnet.lucasapi.domain.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnderecoRequest {

    @NotBlank(message = "O CEP é obrigatório.")
    private String cep;

    @NotBlank(message = "O número é obrigatório.")
    private String numero;

    private String complemento;

    public Endereco toEntity() {
        return Endereco.builder()
                .cep(cep)
                .numero(numero)
                .build();
    }
}
