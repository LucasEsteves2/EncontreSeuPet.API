package edu.infnet.lucasapi.api.controller;

import edu.infnet.lucasapi.api.request.AvistamentoRequest;
import edu.infnet.lucasapi.api.response.AvistamentoResponse;
import edu.infnet.lucasapi.api.response.ApiResponse;
import edu.infnet.lucasapi.application.service.AvistamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avistamentos")
public class AvistamentoController extends BaseController {

    private final AvistamentoService avistamentoService;

    public AvistamentoController(AvistamentoService avistamentoService) {
        this.avistamentoService = avistamentoService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AvistamentoResponse>> criar(@RequestBody @Valid AvistamentoRequest request)
    {
        var avistamento = avistamentoService.criarAvistamento(request.toEntity(), request.getUsuarioId(), request.getPetId());
        return created("/avistamentos", avistamento.getId(), AvistamentoResponse.fromEntity(avistamento));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AvistamentoResponse>> buscarPorId(@PathVariable Long id)
    {
        var avistamento = avistamentoService.buscarPorId(id);
        return ok(AvistamentoResponse.fromEntity(avistamento));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AvistamentoResponse>>> listar(
            @RequestParam(required = false) Long petId,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String descricao,
            Pageable pageable
    )
    {
        var page = avistamentoService.buscarComFiltros(pageable, petId, usuarioId, descricao)
                .map(AvistamentoResponse::fromEntity);

        return ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        avistamentoService.excluir(id);
        return noContent();
    }

}
