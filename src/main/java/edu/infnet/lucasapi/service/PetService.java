package edu.infnet.lucasapi.service;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.repository.PetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PetService extends BaseCrudService<Pet, Long> {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        super(petRepository);
        this.petRepository = petRepository;
    }

    public Page<Pet> buscarComFiltros(Pageable pageable, StatusPet status, Long usuarioId, String raca) {
        var filtros = CriarFiltros(status, usuarioId, raca);
        return petRepository.findAll(filtros, pageable);
    }

    private Specification<Pet> CriarFiltros(StatusPet status, Long usuarioId, String raca) {
        Specification<Pet> filtros = (root, query, cb) -> cb.conjunction(); // condição inicial "sempre verdadeira"

        if (status != null)
            filtros = filtros.and((root, query, cb) -> cb.equal(root.get("status"), status));

        if (usuarioId != null)
            filtros = filtros.and((root, query, cb) -> cb.equal(root.get("usuario").get("id"), usuarioId));

        if (raca != null && !raca.isBlank())
            filtros = filtros.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("raca")), "%" + raca.toLowerCase() + "%"));

        return filtros;
    }


}
