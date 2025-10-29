package edu.infnet.lucasapi.api.controller;

import edu.infnet.lucasapi.api.request.UsuarioRequest;
import edu.infnet.lucasapi.api.response.ApiResponse;
import edu.infnet.lucasapi.api.response.UsuarioResponse;
import edu.infnet.lucasapi.application.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends BaseController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioResponse>> criar(@RequestBody @Valid UsuarioRequest request) {
        var usuario = usuarioService.criar(request.toEntity());
        return created("/usuarios", usuario.getId(), UsuarioResponse.fromEntity(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return noContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioResponse>> buscarPorId(@PathVariable Long id) {
        var usuario = usuarioService.buscarPorId(id);
        return ok(UsuarioResponse.fromEntity(usuario));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UsuarioResponse>>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "99") Integer size,
            @RequestParam(required = false, defaultValue = "id,asc") String sort
    ) {
        Pageable pageable = buildPageable(page, size, sort);
        var usuarios = usuarioService.buscarComFiltros(pageable, nome, email, telefone).map(UsuarioResponse::fromEntity);
        return ok(usuarios);
    }
}
