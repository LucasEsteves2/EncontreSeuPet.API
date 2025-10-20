package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.controller.dto.response.ApiResponseDto;
import edu.infnet.lucasapi.controller.dto.response.NotificacaoResponseDto;
import edu.infnet.lucasapi.service.NotificacaoService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController extends BaseController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<NotificacaoResponseDto>> buscarPorId(@PathVariable Long id) {
        var notificacao = notificacaoService.buscarPorId(id);
        return ok(NotificacaoResponseDto.fromEntity(notificacao));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<NotificacaoResponseDto>>> listar(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Long avistamentoId,
            @RequestParam(required = false) Boolean lida,
            Pageable pageable
    ) {
        var page = notificacaoService.buscarComFiltros(pageable, usuarioId, avistamentoId, lida)
                .map(NotificacaoResponseDto::fromEntity);
        return ok(page);
    }
}
