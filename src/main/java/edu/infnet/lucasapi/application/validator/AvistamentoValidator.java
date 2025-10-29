package edu.infnet.lucasapi.application.validator;

import edu.infnet.lucasapi.domain.exception.AvistamentoException;
import edu.infnet.lucasapi.domain.model.Avistamento;
import edu.infnet.lucasapi.domain.model.Endereco;
import edu.infnet.lucasapi.domain.model.Localizacao;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.domain.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AvistamentoValidator {

    public void validarCriacao(Avistamento avistamento) {

        if (avistamento == null) {
            throw AvistamentoException.invalido(List.of("O avistamento não pode ser nulo."));
        }

        List<String> erros = new ArrayList<>();

        Usuario usuario = avistamento.getUsuario();
        if (usuario == null) {
            erros.add("O usuário do avistamento é obrigatório.");
        } else if (usuario.getId() == null) {
            erros.add("O ID do usuário é obrigatório.");
        }

        Pet pet = avistamento.getPet();
        if (pet == null) {
            erros.add("O pet do avistamento é obrigatório.");
        } else {
            if (pet.getId() == null) {
                erros.add("O ID do pet é obrigatório.");
            }

            Usuario donoDoPet = pet.getUsuario();
            if (donoDoPet != null
                    && donoDoPet.getId() != null
                    && usuario != null
                    && usuario.getId() != null
                    && donoDoPet.getId().equals(usuario.getId())) {
                erros.add("O dono do pet não pode registrar avistamentos do próprio animal.");
            }
        }

        Localizacao localizacao = avistamento.getLocalizacao();
        if (localizacao == null) {
            erros.add("A localização do avistamento é obrigatória.");
        } else {
            if (localizacao.getLatitude() == null || localizacao.getLongitude() == null) {
                erros.add("Latitude e longitude são obrigatórias.");
            }

            Endereco endereco = localizacao.getEndereco();
            if (endereco == null) {
                erros.add("O endereço completo é obrigatório para registrar um avistamento.");
            } else {
                if (isVazio(endereco.getCep())) {
                    erros.add("O CEP é obrigatório e deve estar presente no endereço.");
                }
            }
        }

        if (!erros.isEmpty()) {
            throw AvistamentoException.invalido(erros);
        }
    }

    private boolean isVazio(String valor) {
        return valor == null || valor.isBlank();
    }
}
