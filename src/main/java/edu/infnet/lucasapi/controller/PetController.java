package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.controller.dto.ApiResponseDto;
import edu.infnet.lucasapi.controller.dto.PetRequestDto;
import edu.infnet.lucasapi.controller.dto.PetResponseDto;
import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.service.PetService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController extends BaseController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<PetResponseDto>> criar(@RequestBody @Valid PetRequestDto request) {
        var pet = petService.criar(request.toEntity());
        return created("/pets", pet.getId(), PetResponseDto.fromEntity(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        petService.excluir(id);
        return noContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<PetResponseDto>> buscarPorId(@PathVariable Long id) {
        var pet = petService.buscarPorId(id);
        return ok(PetResponseDto.fromEntity(pet));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<PetResponseDto>>> listar(
            @RequestParam(required = false) StatusPet status,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String raca,
            Pageable pageable
    ) {
        var page = petService.buscarComFiltros(pageable, status, usuarioId, raca).map(PetResponseDto::fromEntity);
        return ok(page);
    }
}