package edu.infnet.lucasapi.api.controller;

import edu.infnet.lucasapi.api.request.AtualizarStatusPetRequest;
import edu.infnet.lucasapi.api.request.PetRequest;
import edu.infnet.lucasapi.api.request.PetUpdateRequest;
import edu.infnet.lucasapi.api.response.ApiResponse;
import edu.infnet.lucasapi.api.response.PetDetailResponse;
import edu.infnet.lucasapi.api.response.PetResponse;
import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.application.service.PetService;
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
    public ResponseEntity<ApiResponse<PetResponse>> criar(@RequestBody @Valid PetRequest dto) {
        var salvo = petService.criar(dto.toEntity(), dto.getUsuarioId());
        return created("/pets", salvo.getId(), PetResponse.fromEntity(salvo));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PetDetailResponse>> buscarPorId(@PathVariable Long id)
    {
        var pet = petService.buscarPorId(id);
        return ok(PetDetailResponse.fromEntity(pet));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PetResponse>>> listar(
            @RequestParam(required = false) StatusPet status,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String raca,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "99") Integer size,
            @RequestParam(required = false, defaultValue = "id,asc") String sort
    )
    {
        Pageable pageable = buildPageable(page, size, sort);
        var pets = petService.buscarComFiltros(pageable, status, usuarioId, raca).map(PetResponse::fromEntity);
        return ok(pets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PetResponse>> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PetUpdateRequest request)
    {
        var atualizado = petService.atualizar(id, request.toEntity());
        return ok(PetResponse.fromEntity(atualizado));
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PetResponse>> atualizarStatus(
            @PathVariable Long id,
            @RequestBody @Valid AtualizarStatusPetRequest request)
    {

        Pet atualizado = petService.atualizarStatus(id, request.getStatus());
        return ok(PetResponse.fromEntity(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id)
    {
        petService.excluir(id);
        return noContent();
    }
}