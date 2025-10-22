package edu.infnet.lucasapi.domain.exception;

import java.util.List;

public class AvistamentoException extends RuntimeException {

    private final List<String> messages;

    private AvistamentoException(List<String> messages) {
        super(messages != null && !messages.isEmpty() ? messages.get(0) : null);
        this.messages = messages;
    }

    public static AvistamentoException invalido(List<String> mensagens) {
        return new AvistamentoException(mensagens);
    }

    public static AvistamentoException naoEncontrado(Long id) {
        return new AvistamentoException(List.of("Avistamento com ID " + id + " não foi encontrado."));
    }

    public List<String> getMessages() {
        return messages;
    }
}
