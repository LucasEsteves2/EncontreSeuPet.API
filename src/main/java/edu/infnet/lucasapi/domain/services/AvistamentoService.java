package edu.infnet.lucasapi.domain.services;

import edu.infnet.lucasapi.domain.model.Avistamento;
import edu.infnet.lucasapi.infrastructure.repository.AvistamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvistamentoService extends BaseCrudService<Avistamento, Long> {

    private final AvistamentoRepository avistamentoRepository;

    public AvistamentoService(AvistamentoRepository avistamentoRepository) {
        super(avistamentoRepository);
        this.avistamentoRepository = avistamentoRepository;
    }

    public List<Avistamento> buscarPorPet(Long petId) {
        return avistamentoRepository.findByPetId(petId);
    }

    public List<Avistamento> buscarPorUsuario(Long usuarioId) {
        return avistamentoRepository.findByUsuarioId(usuarioId);
    }
}
