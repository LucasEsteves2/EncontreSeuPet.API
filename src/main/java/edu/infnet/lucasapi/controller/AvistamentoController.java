package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.controller.dto.request.AvistamentoRequestDto;
import edu.infnet.lucasapi.controller.dto.response.AvistamentoResponseDto;
import edu.infnet.lucasapi.controller.dto.response.ApiResponseDto;
import edu.infnet.lucasapi.service.AvistamentoService;
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
    public ResponseEntity<ApiResponseDto<AvistamentoResponseDto>> criar(@RequestBody @Valid AvistamentoRequestDto request) {
        var avistamento = avistamentoService.criar(request.toEntity());
        return created("/avistamentos", avistamento.getId(), AvistamentoResponseDto.fromEntity(avistamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        avistamentoService.excluir(id);
        return noContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<AvistamentoResponseDto>> buscarPorId(@PathVariable Long id)
    {
        var avistamento = avistamentoService.buscarPorId(id);
        return ok(AvistamentoResponseDto.fromEntity(avistamento));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<AvistamentoResponseDto>>> listar(
            @RequestParam(required = false) Long petId,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String descricao,
            Pageable pageable
    )
    {
        var page = avistamentoService.buscarComFiltros(pageable, petId, usuarioId, descricao)
                .map(AvistamentoResponseDto::fromEntity);

        return ok(page);
    }
}
