package edu.infnet.lucasapi.api.response;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PetDetailResponse {
    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private String cor;
    private String descricao;
    private StatusPet status;
    private String fotoUrl;
    private Long usuarioId;
    private String usuarioNome;
    private List<AvistamentoResponse> avistamentos;

    public static PetDetailResponse fromEntity(Pet pet) {
        return PetDetailResponse.builder()
                .id(pet.getId())
                .nome(pet.getNome())
                .especie(pet.getEspecie())
                .raca(pet.getRaca())
                .cor(pet.getCor())
                .descricao(pet.getDescricao())
                .status(pet.getStatus())
                .fotoUrl(pet.getFotoUrl())
                .usuarioId(pet.getUsuario() != null ? pet.getUsuario().getId() : null)
                .usuarioNome(pet.getUsuario() != null ? pet.getUsuario().getNome() : null)
                .avistamentos(pet.getAvistamentos() != null
                        ? AvistamentoResponse.fromEntities(pet.getAvistamentos())
                        : null)
                .build();
    }
}
