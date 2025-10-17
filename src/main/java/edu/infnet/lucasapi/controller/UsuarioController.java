package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.controller.dto.response.ApiResponseDto;
import edu.infnet.lucasapi.controller.dto.request.UsuarioRequestDto;
import edu.infnet.lucasapi.controller.dto.response.UsuarioResponseDto;
import edu.infnet.lucasapi.service.UsuarioService;
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
    public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> criar(@RequestBody @Valid UsuarioRequestDto request) {
        var usuario = usuarioService.criar(request.toEntity());
        return created("/usuarios", usuario.getId(), UsuarioResponseDto.fromEntity(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluir(id);
        return noContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> buscarPorId(@PathVariable Long id) {
        var usuario = usuarioService.buscarPorId(id);
        return ok(UsuarioResponseDto.fromEntity(usuario));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<UsuarioResponseDto>>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String telefone,
            Pageable pageable
    ) {
        var page = usuarioService.buscarComFiltros(pageable, nome, email, telefone)
                .map(UsuarioResponseDto::fromEntity);
        return ok(page);
    }


}
