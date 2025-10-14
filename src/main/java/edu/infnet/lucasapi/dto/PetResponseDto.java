package edu.infnet.lucasapi.dto;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PetResponseDto {
    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private String cor;
    private StatusPet status;
    private String fotoUrl;
    private String usuarioNome;

    public static PetResponseDto fromEntity(Pet pet) {
        return PetResponseDto.builder()
                .id(pet.getId())
                .nome(pet.getNome())
                .especie(pet.getEspecie())
                .raca(pet.getRaca())
                .cor(pet.getCor())
                .status(pet.getStatus())
                .fotoUrl(pet.getFotoUrl())
                .usuarioNome(pet.getUsuario() != null ? pet.getUsuario().getNome() : null)
                .build();
    }

    public static List<PetResponseDto> fromEntities(List<Pet> pets) {
        return pets.stream()
                .map(PetResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}