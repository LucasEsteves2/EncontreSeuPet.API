package edu.infnet.lucasapi.api.request;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.api.validation.ValidEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PetRequest {

    @NotBlank(message = "O nome do pet é obrigatório.")
    @Size(min = 2, max = 80, message = "O nome deve ter entre 2 e 80 caracteres.")
    private String nome;

    @NotBlank(message = "A espécie é obrigatória.")
    private String especie;

    @NotBlank(message = "A raça é obrigatória.")
    private String raca;

    @NotBlank(message = "O status do pet é obrigatório.")
    @ValidEnum(enumClass = StatusPet.class)
    private String status;

    @NotNull(message = "O ID do usuário é obrigatório.")
    @Min(value = 1, message = "O ID do usuário deve ser maior que zero.")
    private Long usuarioId;

    public Pet toEntity() {
        return Pet.builder()
                .nome(nome)
                .especie(especie)
                .raca(raca)
                .status(StatusPet.valueOf(status.toUpperCase()))
                .build();
    }
}
