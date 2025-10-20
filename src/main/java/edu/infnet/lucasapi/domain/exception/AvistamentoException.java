package edu.infnet.lucasapi.domain.exception;

public class AvistamentoException extends RuntimeException {

    public AvistamentoException(String mensagem) {
        super(mensagem);
    }

    public static AvistamentoException invalido(String mensagem) {
        return new AvistamentoException(mensagem);
    }

    public static AvistamentoException naoEncontrado(Long id) {
        return new AvistamentoException("Avistamento com ID " + id + " não foi encontrado.");
    }
}
