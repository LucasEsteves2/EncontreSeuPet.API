package edu.infnet.lucasapi.infrastructure.adapters.viacep;

public interface ViaCepClient {
    ViaCepResponse buscarPorCep(String cepSomenteDigitos);
}