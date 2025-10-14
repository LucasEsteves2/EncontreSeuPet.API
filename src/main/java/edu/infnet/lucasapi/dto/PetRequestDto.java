package edu.infnet.lucasapi.dto;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import lombok.Data;

@Data
public class PetRequestDto {
    private String nome;
    private String especie;
    private String raca;
    private String cor;
    private String descricao;
    private String fotoUrl;
    private StatusPet status;
    private Long usuarioId;

    public Pet toEntity() {
        Pet pet = new Pet();
        pet.setNome(this.nome);
        pet.setEspecie(this.especie);
        pet.setRaca(this.raca);
        pet.setCor(this.cor);
        pet.setDescricao(this.descricao);
        pet.setFotoUrl(this.fotoUrl);
        pet.setStatus(this.status);
        return pet;
    }
}