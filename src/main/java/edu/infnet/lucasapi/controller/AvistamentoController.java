package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.domain.model.Avistamento;
import edu.infnet.lucasapi.service.AvistamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/avistamentos")
public class AvistamentoController {

    private final AvistamentoService avistamentoService;

    public AvistamentoController(AvistamentoService avistamentoService) {
        this.avistamentoService = avistamentoService;
    }

    @PostMapping
    public ResponseEntity<Avistamento> criar(@RequestBody Avistamento avistamento) {
        avistamentoService.criar(avistamento);
        return ResponseEntity.created(URI.create("/avistamentos/" + avistamento.getId())).body(avistamento);
    }

//    @GetMapping
//    public ResponseEntity<List<Avistamento>> listarTodos() {
//        return ResponseEntity.ok(avistamentoService.listarTodos());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Avistamento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(avistamentoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        avistamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Avistamento>> listarPorPet(@PathVariable Long petId) {
        return ResponseEntity.ok(avistamentoService.buscarPorPet(petId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Avistamento>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(avistamentoService.buscarPorUsuario(usuarioId));
    }
}