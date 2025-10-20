package edu.infnet.lucasapi.domain.validator;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.exception.PetException;
import edu.infnet.lucasapi.domain.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetValidator {

    public void validarCriacao(Pet pet) {
        if (pet == null)
            throw PetException.invalido("O pet não pode ser nulo.");

        if (pet.getUsuario() == null)
            throw PetException.invalido("O pet deve estar associado a um usuário válido.");

        if (isVazio(pet.getNome()))
            throw PetException.invalido("O nome do pet é obrigatório.");

        if (isVazio(pet.getEspecie()))
            throw PetException.invalido("A espécie do pet é obrigatória.");

        if (pet.getStatus() == null)
            throw PetException.invalido("O status do pet deve ser informado.");
    }

    public void validarAtualizacao(Pet existente, Pet atualizado) {
        if (atualizado == null)
            throw PetException.invalido("Os dados do pet para atualização não podem ser nulos.");

        if (!existente.getId().equals(atualizado.getId()) && atualizado.getId() != null)
            throw PetException.invalido("O ID do pet não pode ser alterado.");

        validarCriacao(atualizado);
    }

    public void validarAtualizacaoStatus(Pet pet, StatusPet novoStatus) {
        if (novoStatus == null)
            throw PetException.invalido("O novo status não pode ser nulo.");

        if (pet.getStatus() == novoStatus)
            throw PetException.statusInvalido(
                    String.format("O status do pet já está definido como '%s'. Nenhuma atualização foi realizada.", novoStatus)
            );


    }

    private boolean isVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
