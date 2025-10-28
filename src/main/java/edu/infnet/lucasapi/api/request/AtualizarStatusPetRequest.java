package edu.infnet.lucasapi.api.request;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarStatusPetRequest {
    @NotNull(message = "O status é obrigatório.")
    private StatusPet status;
}
