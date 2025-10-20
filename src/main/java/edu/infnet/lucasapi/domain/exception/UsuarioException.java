package edu.infnet.lucasapi.domain.exception;

public class UsuarioException extends RuntimeException {

    private UsuarioException(String mensagem) {
        super(mensagem);
    }

    public static UsuarioException naoEncontrado(Long id) {
        return new UsuarioException(String.format("Usuário não encontrado para o ID: %s", id));
    }

    public static UsuarioException invalido(String mensagem) {
        return new UsuarioException("Usuário inválido: " + mensagem);
    }

    public static UsuarioException jaExistente(String email) {
        return new UsuarioException(String.format("Já existe um usuário cadastrado com o e-mail: %s", email));
    }
}
