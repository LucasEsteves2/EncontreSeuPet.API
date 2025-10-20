package edu.infnet.lucasapi.domain.exception;

public class PetException extends RuntimeException {

    private PetException(String mensagem) {
        super(mensagem);
    }

    public static PetException naoEncontrado(Long id) {
        return new PetException("Pet com ID " + id + " não foi encontrado.");
    }

    public static PetException invalido(String motivo) {
        return new PetException("Pet inválido: " + motivo);
    }

    public static PetException statusInvalido(String detalhe) {
        return new PetException("Status inválido: " + detalhe);
    }

    public static PetException jaCadastrado(String nome) {
        return new PetException("Já existe um pet cadastrado com o nome '" + nome + "'.");
    }
}
