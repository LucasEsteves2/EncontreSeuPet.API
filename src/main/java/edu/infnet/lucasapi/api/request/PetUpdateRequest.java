package edu.infnet.lucasapi.api.request;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.api.validation.ValidEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PetUpdateRequest {

    private String nome;
    private String especie;
    private String raca;

    @Min(value = 0, message = "A idade do pet não pode ser negativa.")
    @Max(value = 30, message = "A idade do pet não pode exceder 30 anos.")
    private Integer idade;

    @PastOrPresent(message = "A data de desaparecimento deve ser no passado ou presente.")
    private LocalDate desaparecidoEm;

    @ValidEnum(enumClass = StatusPet.class)
    private String status;
    public Pet toEntity() {
        return Pet.builder()
                .nome(nome)
                .especie(especie)
                .raca(raca)
                .idade(idade)
                .desaparecidoEm(desaparecidoEm)
                .status(status != null ? StatusPet.valueOf(status.toUpperCase()) : null)
                .build();
    }
}
