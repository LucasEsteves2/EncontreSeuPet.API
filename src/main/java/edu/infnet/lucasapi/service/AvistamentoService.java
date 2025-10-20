package edu.infnet.lucasapi.service;

import edu.infnet.lucasapi.domain.exception.AvistamentoException;
import edu.infnet.lucasapi.domain.model.Avistamento;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.domain.validator.AvistamentoValidator;
import edu.infnet.lucasapi.repository.AvistamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AvistamentoService extends BaseCrudService<Avistamento, Long> {

    private final AvistamentoRepository avistamentoRepository;
    private final AvistamentoValidator avistamentoValidator;
    private final NotificacaoService notificacaoService;
    private final UsuarioService usuarioService;
    private final PetService petService;

    public AvistamentoService(AvistamentoRepository avistamentoRepository, AvistamentoValidator avistamentoValidator, NotificacaoService notificacaoService, UsuarioService usuarioService, PetService petService) {
        super(avistamentoRepository);
        this.avistamentoRepository = avistamentoRepository;
        this.avistamentoValidator = avistamentoValidator;
        this.notificacaoService = notificacaoService;
        this.usuarioService = usuarioService;
        this.petService = petService;
    }

    public Avistamento criarAvistamento(Avistamento avistamento, Long usuarioId, Long petId)
    {
        var usuario = usuarioService.buscarPorId(usuarioId);
        var pet = petService.buscarPorId(petId);

        avistamento.setUsuario(usuario);
        avistamento.setPet(pet);

        avistamentoValidator.validarCriacao(avistamento);

        avistamento.getLocalizacao().setAvistamento(avistamento);
        var salvo = super.criar(avistamento);

        notificarDonoDoPet(salvo);
        return salvo;
    }


    public Page<Avistamento> buscarComFiltros(Pageable pageable, Long petId, Long usuarioId, String descricao) {
        var filtros = criarFiltros(petId, usuarioId, descricao);
        return avistamentoRepository.findAll(filtros, pageable);
    }

    private Specification<Avistamento> criarFiltros(Long petId, Long usuarioId, String descricao) {
        Specification<Avistamento> filtros = (root, query, cb) -> cb.conjunction();

        if (petId != null)
            filtros = filtros.and((root, query, cb) -> cb.equal(root.get("pet").get("id"), petId));

        if (usuarioId != null)
            filtros = filtros.and((root, query, cb) -> cb.equal(root.get("usuario").get("id"), usuarioId));

        if (descricao != null && !descricao.isBlank())
            filtros = filtros.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("descricao")), "%" + descricao.toLowerCase() + "%"));

        return filtros;
    }

    private void notificarDonoDoPet(Avistamento avistamento) {
        var pet = avistamento.getPet();
        var usuario = pet.getUsuario();
        var mensagem = montarMensagemDeAvistamento(pet, avistamento);

        notificacaoService.criarNotificacao(usuario, mensagem, avistamento);
    }

    private String montarMensagemDeAvistamento(Pet pet, Avistamento avistamento) {
        var nomePet = pet.getNome() != null ? pet.getNome() : "seu pet";
        var local = "no bairro " + avistamento.getLocalizacao().getEndereco().getBairro();
        return String.format("%s foi avistado recentemente %s.", nomePet, local);
    }
}
