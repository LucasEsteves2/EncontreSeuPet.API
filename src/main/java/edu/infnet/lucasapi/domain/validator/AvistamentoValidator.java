package edu.infnet.lucasapi.domain.validator;

import edu.infnet.lucasapi.domain.exception.AvistamentoException;
import edu.infnet.lucasapi.domain.model.Avistamento;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AvistamentoValidator {

    public void validarCriacao(Avistamento avistamento) {

        if (avistamento == null)
            throw AvistamentoException.invalido(List.of("O avistamento não pode ser nulo."));

        List<String> erros = new ArrayList<>();

        if (avistamento.getUsuario() == null) {
            erros.add("O usuário do avistamento é obrigatório.");
        } else if (avistamento.getUsuario().getId() == null) {
            erros.add("O ID do usuário é obrigatório.");
        }

        if (avistamento.getPet() == null) {
            erros.add("O pet do avistamento é obrigatório.");
        } else {
            var pet = avistamento.getPet();

            if (pet.getId() == null)
                erros.add("O ID do pet é obrigatório.");

            if (pet.getUsuario() != null &&
                    pet.getUsuario().getId() != null &&
                    avistamento.getUsuario() != null &&
                    pet.getUsuario().getId().equals(avistamento.getUsuario().getId())) {
                erros.add("O dono do pet não pode registrar avistamentos do próprio animal.");
            }
        }


        if (avistamento.getLocalizacao() == null) {
            erros.add("A localização do avistamento é obrigatória.");
        } else {
            var loc = avistamento.getLocalizacao();

            if (loc.getLatitude() == null || loc.getLongitude() == null)
                erros.add("Latitude e longitude são obrigatórias.");

            if (loc.getEndereco() == null)
                erros.add("O endereço completo é obrigatório para registrar um avistamento.");
            else if (isVazio(loc.getEndereco().getCep()))
                erros.add("O CEP é obrigatório e deve estar presente no endereço.");
        }

        if (!erros.isEmpty())
            throw AvistamentoException.invalido(erros);
    }

    private boolean isVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
