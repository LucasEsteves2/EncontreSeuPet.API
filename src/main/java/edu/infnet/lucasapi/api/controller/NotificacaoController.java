package edu.infnet.lucasapi.api.controller;

import edu.infnet.lucasapi.api.response.ApiResponse;
import edu.infnet.lucasapi.api.response.NotificacaoResponse;
import edu.infnet.lucasapi.application.service.NotificacaoService;
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
    public ResponseEntity<ApiResponse<NotificacaoResponse>> buscarPorId(@PathVariable Long id) {
        var notificacao = notificacaoService.buscarPorId(id);
        return ok(NotificacaoResponse.fromEntity(notificacao));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificacaoResponse>>> listar(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Long avistamentoId,
            @RequestParam(required = false) Boolean lida,
            Pageable pageable
    ) {
        var page = notificacaoService.buscarComFiltros(pageable, usuarioId, avistamentoId, lida)
                .map(NotificacaoResponse::fromEntity);
        return ok(page);
    }
}
