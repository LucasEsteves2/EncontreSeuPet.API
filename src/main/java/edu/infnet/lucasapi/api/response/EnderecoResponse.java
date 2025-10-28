package edu.infnet.lucasapi.api.response;

import edu.infnet.lucasapi.domain.model.Endereco;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoResponse {

    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public static EnderecoResponse fromEntity(Endereco e) {
        if (e == null) return null;

        return EnderecoResponse.builder()
                .rua(e.getRua())
                .numero(e.getNumero())
                .bairro(e.getBairro())
                .cidade(e.getCidade())
                .estado(e.getEstado())
                .cep(e.getCep())
                .build();
    }
}
