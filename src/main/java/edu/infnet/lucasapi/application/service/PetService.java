package edu.infnet.lucasapi.application.service;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.exception.PetException;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.application.validator.PetValidator;
import edu.infnet.lucasapi.infrastructure.repository.PetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PetService extends BaseCrudService<Pet, Long> {

    private final PetRepository petRepository;
    private final PetValidator petValidator;
    private final UsuarioService usuarioService;

    public PetService(PetRepository petRepository, PetValidator petValidator, UsuarioService usuarioService) {
        super(petRepository);
        this.petRepository = petRepository;
        this.petValidator = petValidator;
        this.usuarioService = usuarioService;
    }

    @Override
    public Pet buscarPorId(Long id) {
        var pet = super.buscarPorId(id);

        if (pet == null)
            throw PetException.naoEncontrado(id);

        return pet;
    }

    public Pet criar(Pet pet, long usuarioId)
    {
        var usuario = usuarioService.buscarPorId(usuarioId);
        pet.setUsuario(usuario);

        petValidator.validarCriacao(pet);
        return super.criar(pet);
    }

    @Override
    public Pet atualizar(Long id, Pet petAtualizado) {
        var pet = petRepository.findById(id).orElseThrow(() -> PetException.naoEncontrado(id));
        petAtualizado.setUsuario(pet.getUsuario());

        petValidator.validarAtualizacao(pet, petAtualizado);

        petAtualizado.setId(pet.getId());
        petAtualizado.setAvistamentos(pet.getAvistamentos());

        return super.atualizar(id, petAtualizado);
    }

    public Pet atualizarStatus(Long id, StatusPet novoStatus) {
        var pet = buscarPorId(id);

        petValidator.validarAtualizacaoStatus(pet, novoStatus);

        pet.setStatus(novoStatus);
        return repository.save(pet);
    }

    public Page<Pet> buscarComFiltros(Pageable pageable, StatusPet status, Long usuarioId, String raca) {
        var filtros = criarFiltros(status, usuarioId, raca);
        return petRepository.findAll(filtros, pageable);
    }

    private Specification<Pet> criarFiltros(StatusPet status, Long usuarioId, String raca) {
        Specification<Pet> filtros = (root, query, cb) -> cb.conjunction();

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
