package edu.infnet.lucasapi.domain.exception;

import java.util.Collections;
import java.util.List;

public class UsuarioException extends RuntimeException {

    private final List<String> messages;

    private UsuarioException(String message) {
        super(message);
        this.messages = Collections.singletonList(message);
    }

    private UsuarioException(List<String> messages) {
        super(messages != null && !messages.isEmpty() ? messages.get(0) : "Erro de validação no usuário.");
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }

    public static UsuarioException naoEncontrado(Long id) {
        return new UsuarioException(String.format("Usuário não encontrado para o ID: %s", id));
    }

    public static UsuarioException invalido(String message) {
        return new UsuarioException(message);
    }

    public static UsuarioException invalido(List<String> messages) {
        return new UsuarioException(messages);
    }

    public static UsuarioException jaExistente(String email) {
        return new UsuarioException(String.format("Já existe um usuário cadastrado com o e-mail: %s", email));
    }
}
