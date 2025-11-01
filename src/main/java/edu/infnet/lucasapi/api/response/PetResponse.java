package edu.infnet.lucasapi.api.response;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PetResponse {
    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private String cor;
    private Integer idade;
    private LocalDate desaparecidoEm;
    private StatusPet status;
    private String fotoUrl;
    private Long usuarioId;
    private String usuarioNome;

    public static PetResponse fromEntity(Pet pet) {
        return PetResponse.builder()
                .id(pet.getId())
                .nome(pet.getNome())
                .especie(pet.getEspecie())
                .raca(pet.getRaca())
                .cor(pet.getCor())
                .idade(pet.getIdade())
                .desaparecidoEm(pet.getDesaparecidoEm())
                .status(pet.getStatus())
                .fotoUrl(pet.getFotoUrl())
                .usuarioId(pet.getUsuario() != null ? pet.getUsuario().getId() : null)
                .usuarioNome(pet.getUsuario() != null ? pet.getUsuario().getNome() : null)
                .build();
    }

    public static List<PetResponse> fromEntities(List<Pet> pets) {
        if (pets == null) return null;

        return pets.stream()
                .map(PetResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
