package edu.infnet.lucasapi.api.request;

import edu.infnet.lucasapi.domain.model.Endereco;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnderecoRequest {

    @NotBlank(message = "A rua é obrigatória.")
    private String rua;

    private String numero;

    @NotBlank(message = "O bairro é obrigatório.")
    private String bairro;

    @NotBlank(message = "A cidade é obrigatória.")
    private String cidade;

    @NotBlank(message = "O estado é obrigatório.")
    private String estado;

    @NotBlank(message = "O CEP é obrigatório.")
    private String cep;

    public Endereco toEntity() {
        return Endereco.builder()
                .rua(rua)
                .numero(numero)
                .bairro(bairro)
                .cidade(cidade)
                .estado(estado)
                .cep(cep)
                .build();
    }
}
