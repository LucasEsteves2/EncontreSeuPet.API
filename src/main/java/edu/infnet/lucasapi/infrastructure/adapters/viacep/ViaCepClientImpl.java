package edu.infnet.lucasapi.infrastructure.adapters.viacep;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClientImpl implements ViaCepClient {

    private final RestTemplate restTemplate;

    public ViaCepClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ViaCepResponse buscarPorCep(String cep) {
        return restTemplate.getForObject(
                "https://viacep.com.br/ws/{cep}/json/",
                ViaCepResponse.class,
                cep
        );
    }
}