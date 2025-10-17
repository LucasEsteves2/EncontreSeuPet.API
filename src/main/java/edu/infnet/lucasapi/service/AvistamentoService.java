package edu.infnet.lucasapi.service;

import edu.infnet.lucasapi.domain.model.Avistamento;
import edu.infnet.lucasapi.repository.AvistamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AvistamentoService extends BaseCrudService<Avistamento, Long> {

    private final AvistamentoRepository avistamentoRepository;
    private final NotificacaoService notificacaoService;

    public AvistamentoService(AvistamentoRepository avistamentoRepository, NotificacaoService notificacaoService) {
        super(avistamentoRepository);
        this.avistamentoRepository = avistamentoRepository;
        this.notificacaoService = notificacaoService;
    }

    public Avistamento criar(Avistamento avistamento) {

        if (avistamento.getLocalizacao() != null) {
            avistamento.getLocalizacao().setAvistamento(avistamento);
        }

        var salvo = super.criar(avistamento);

        var usuario = salvo.getPet().getUsuario();
        var pet = salvo.getPet();

        var mensagem = "Seu pet " + pet.getNome() + " foi avistado em " + formatarLocalizacao(salvo);

        notificacaoService.criarNotificacao(usuario, mensagem, salvo);

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


    private String formatarLocalizacao(Avistamento a) {
        if (a.getLocalizacao() == null || a.getLocalizacao().getEndereco() == null)
            return "localização não especificada";

        var end = a.getLocalizacao().getEndereco();
        return String.format("%s, %s - %s",
                end.getRua(),
                end.getBairro(),
                end.getCidade());
    }
}
