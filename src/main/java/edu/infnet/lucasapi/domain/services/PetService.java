package edu.infnet.lucasapi.domain.services;


import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.infrastructure.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService extends BaseCrudService<Pet, Long> {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        super(petRepository);
        this.petRepository = petRepository;
    }

    public List<Pet> buscarPorStatus(StatusPet status) {
        return petRepository.findByStatus(status);
    }

    public List<Pet> buscarPorUsuario(Long usuarioId) {
        return petRepository.findByUsuarioId(usuarioId);
    }
}