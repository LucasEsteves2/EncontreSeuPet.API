package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.domain.model.Notificacao;
import edu.infnet.lucasapi.domain.services.NotificacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping
    public ResponseEntity<List<Notificacao>> listarTodas() {
        return ResponseEntity.ok(notificacaoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notificacaoService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacao>> buscarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacaoService.buscarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/nao-lidas")
    public ResponseEntity<List<Notificacao>> buscarNaoLidasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacaoService.buscarNaoLidasPorUsuario(usuarioId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        notificacaoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
