package edu.infnet.lucasapi.domain.exception;

import java.util.List;

public class PetException extends RuntimeException {

    private final List<String> messages;

    private PetException(List<String> messages) {
        super(messages != null && !messages.isEmpty() ? messages.get(0) : null);
        this.messages = messages;
    }

    public static PetException invalido(List<String> mensagens) {
        return new PetException(mensagens);
    }

    public static PetException naoEncontrado(Long id) {
        return new PetException(List.of("Pet com ID " + id + " não foi encontrado."));
    }

    public static PetException statusInvalido(String detalhe) {
        return new PetException(List.of("Status inválido: " + detalhe));
    }

    public static PetException jaCadastrado(String nome) {
        return new PetException(List.of("Já existe um pet cadastrado com o nome '" + nome + "'."));
    }

    public List<String> getMessages() {
        return messages;
    }
}
