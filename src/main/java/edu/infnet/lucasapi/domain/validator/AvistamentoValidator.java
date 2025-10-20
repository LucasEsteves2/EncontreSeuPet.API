package edu.infnet.lucasapi.domain.validator;

import edu.infnet.lucasapi.domain.exception.AvistamentoException;
import edu.infnet.lucasapi.domain.model.Avistamento;
import org.springframework.stereotype.Component;

@Component
public class AvistamentoValidator {

    public void validarCriacao(Avistamento avistamento) {

        if (avistamento == null)
            throw new AvistamentoException("O avistamento não pode ser nulo.");

        validarUsuario(avistamento);
        validarPet(avistamento);
        validarLocalizacao(avistamento);
    }

    private void validarUsuario(Avistamento avistamento) {
        var usuario = avistamento.getUsuario();

        if (usuario == null)
            throw new AvistamentoException("O usuário do avistamento é obrigatório.");

        if (usuario.getId() == null)
            throw new AvistamentoException("O ID do usuário é obrigatório.");
    }

    private void validarPet(Avistamento avistamento) {
        var pet = avistamento.getPet();

        if (pet == null)
            throw new AvistamentoException("O pet do avistamento é obrigatório.");

        if (pet.getId() == null)
            throw new AvistamentoException("O ID do pet é obrigatório.");

        if (pet.getUsuario() != null && pet.getUsuario().getId() != null &&
                pet.getUsuario().getId().equals(avistamento.getUsuario().getId())) {
            throw new AvistamentoException("O dono do pet não pode registrar avistamentos do próprio animal.");
        }
    }

    private void validarLocalizacao(Avistamento avistamento) {
        var loc = avistamento.getLocalizacao();

        if (loc == null)
            throw new AvistamentoException("A localização do avistamento é obrigatória.");

        if (loc.getLatitude() == null || loc.getLongitude() == null)
            throw new AvistamentoException("Latitude e longitude são obrigatórias.");

        if (loc.getEndereco() == null)
            throw new AvistamentoException("O endereço completo é obrigatório para registrar um avistamento.");
    }
}
