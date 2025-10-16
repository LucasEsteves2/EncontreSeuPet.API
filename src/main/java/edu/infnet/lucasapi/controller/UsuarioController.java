package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.domain.model.Usuario;
import edu.infnet.lucasapi.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
        usuarioService.criar(usuario);
        return ResponseEntity.created(URI.create("/usuarios/" + usuario.getId())).body(usuario);
    }

//    @GetMapping
//    public ResponseEntity<List<Usuario>> listarTodos() {
//        return ResponseEntity.ok(usuarioService.listarTodos());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.buscarPorEmail(email));
    }
}
