package edu.infnet.lucasapi.controller.dto.response;

import edu.infnet.lucasapi.domain.model.Endereco;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoResponseDto {

    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public static EnderecoResponseDto fromEntity(Endereco e) {
        if (e == null) return null;

        return EnderecoResponseDto.builder()
                .rua(e.getRua())
                .numero(e.getNumero())
                .bairro(e.getBairro())
                .cidade(e.getCidade())
                .estado(e.getEstado())
                .cep(e.getCep())
                .build();
    }
}
