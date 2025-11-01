package edu.infnet.lucasapi.api.request;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.api.validation.ValidEnum;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PetRequest {

    @NotBlank(message = "O nome do pet é obrigatório.")
    @Size(min = 2, max = 80, message = "O nome deve ter entre 2 e 80 caracteres.")
    private String nome;

    @NotBlank(message = "A espécie é obrigatória.")
    private String especie;

    @NotBlank(message = "A raça é obrigatória.")
    private String raca;

    @NotNull(message = "A idade é obrigatória.")
    @Min(value = 0, message = "A idade do pet não pode ser negativa.")
    @Max(value = 30, message = "A idade do pet não pode exceder 30 anos.")
    private Integer idade;

    //opcional, caso n seja informado usa a data atual no builder..
    //adicionei a validacao temporal!
    @PastOrPresent(message = "A data de desaparecimento deve ser no passado ou presente.")
    private LocalDate desaparecidoEm;

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
                .idade(idade)
                .desaparecidoEm(desaparecidoEm != null ? desaparecidoEm : LocalDate.now())
                .status(StatusPet.valueOf(status.toUpperCase()))
                .build();
    }
}
