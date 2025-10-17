package edu.infnet.lucasapi.controller.dto.request;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PetRequestDto {

    @NotBlank(message = "O nome do pet é obrigatório.")
    private String nome;

    @NotBlank(message = "A espécie é obrigatória.")
    private String especie;

    private String raca;

    @NotNull(message = "O status do pet é obrigatório.")
    private StatusPet status;

    public Pet toEntity() {
        return Pet.builder()
                .nome(nome)
                .especie(especie)
                .raca(raca)
                .status(status)
                .build();
    }
}