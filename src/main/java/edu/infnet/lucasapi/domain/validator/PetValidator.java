package edu.infnet.lucasapi.domain.validator;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.exception.PetException;
import edu.infnet.lucasapi.domain.model.Pet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PetValidator {

    public void validarCriacao(Pet pet) {
        if (pet == null)
            throw PetException.invalido(List.of("O pet não pode ser nulo."));

        List<String> erros = new ArrayList<>();

        if (pet.getUsuario() == null)
            erros.add("O pet deve estar associado a um usuário válido.");

        if (isVazio(pet.getNome()))
            erros.add("O nome do pet é obrigatório.");

        if (isVazio(pet.getEspecie()))
            erros.add("A espécie do pet é obrigatória.");

        if (pet.getStatus() == null)
            erros.add("O status do pet deve ser informado.");

        if (!erros.isEmpty())
            throw PetException.invalido(erros);
    }

    public void validarAtualizacao(Pet existente, Pet atualizado) {
        if (atualizado == null)
            throw PetException.invalido(List.of("Os dados do pet para atualização não podem ser nulos."));

        List<String> erros = new ArrayList<>();

        if (atualizado.getId() != null && !existente.getId().equals(atualizado.getId()))
            erros.add("O ID do pet não pode ser alterado.");

        if (!erros.isEmpty())
            throw PetException.invalido(erros);

        validarCriacao(atualizado);
    }

    public void validarAtualizacaoStatus(Pet pet, StatusPet novoStatus) {
        if (novoStatus == null)
            throw PetException.invalido(List.of("O novo status não pode ser nulo."));

        if (pet.getStatus() == novoStatus)
            throw PetException.invalido(List.of(
                    String.format("O status do pet já está definido como '%s'. Nenhuma atualização foi realizada.", novoStatus)
            ));
    }

    private boolean isVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
