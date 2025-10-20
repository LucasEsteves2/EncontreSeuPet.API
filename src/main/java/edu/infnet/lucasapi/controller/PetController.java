package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.controller.dto.request.AtualizarStatusPetRequest;
import edu.infnet.lucasapi.controller.dto.request.PetRequestDto;
import edu.infnet.lucasapi.controller.dto.response.ApiResponseDto;
import edu.infnet.lucasapi.controller.dto.response.PetDetailResponseDto;
import edu.infnet.lucasapi.controller.dto.response.PetResponseDto;
import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.service.PetService;
import edu.infnet.lucasapi.service.UsuarioService;
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
    public ResponseEntity<ApiResponseDto<PetResponseDto>> criar(@RequestBody @Valid PetRequestDto dto) {
        var salvo = petService.criar(dto.toEntity(), dto.getUsuarioId());
        return created("/pets", salvo.getId(), PetResponseDto.fromEntity(salvo));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<PetDetailResponseDto>> buscarPorId(@PathVariable Long id)
    {
        var pet = petService.buscarPorId(id);
        return ok(PetDetailResponseDto.fromEntity(pet));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<PetResponseDto>>> listar(
            @RequestParam(required = false) StatusPet status,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String raca,
            Pageable pageable
    )
    {
        var page = petService.buscarComFiltros(pageable, status, usuarioId, raca).map(PetResponseDto::fromEntity);
        return ok(page);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<PetResponseDto>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PetRequestDto request)
    {

        var atualizado = petService.atualizar(id, request.toEntity());
        return ok(PetResponseDto.fromEntity(atualizado));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponseDto<PetResponseDto>> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarStatusPetRequest request)
    {

        Pet atualizado = petService.atualizarStatus(id, request.getStatus());
        return ok(PetResponseDto.fromEntity(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id)
    {
        petService.excluir(id);
        return noContent();
    }
}