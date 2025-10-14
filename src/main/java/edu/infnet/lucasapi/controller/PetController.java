package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.domain.services.PetService;
import edu.infnet.lucasapi.dto.ApiResponse;
import edu.infnet.lucasapi.dto.PetRequestDto;
import edu.infnet.lucasapi.dto.PetResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PetResponseDto>> criar(@RequestBody PetRequestDto request) {
        Pet pet = petService.criar(request.toEntity());
        PetResponseDto response = PetResponseDto.fromEntity(pet);

        return ResponseEntity
                .created(URI.create("/pets/" + pet.getId()))
                .body(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Pet pet = petService.buscarPorId(id);

        if (pet == null) {
            throw new EntityNotFoundException("Pet não encontrado com ID: " + id);
        }

        petService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PetResponseDto>> buscarPorId(@PathVariable Long id) {
        Pet pet = petService.buscarPorId(id);

        if (pet == null) {
            throw new EntityNotFoundException("Pet não encontrado com ID: " + id);
        }

        return ResponseEntity.ok(ApiResponse.success(PetResponseDto.fromEntity(pet)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PetResponseDto>>> listarTodos() {
        List<Pet> pets = petService.listarTodos();
        return ResponseEntity.ok(ApiResponse.success(PetResponseDto.fromEntities(pets)));
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<PetResponseDto>>> buscarPorStatus(@PathVariable StatusPet status) {
        List<Pet> pets = petService.buscarPorStatus(status);
        return ResponseEntity.ok(ApiResponse.success(PetResponseDto.fromEntities(pets)));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ApiResponse<List<PetResponseDto>>> buscarPorUsuario(@PathVariable Long usuarioId) {
        List<Pet> pets = petService.buscarPorUsuario(usuarioId);
        return ResponseEntity.ok(ApiResponse.success(PetResponseDto.fromEntities(pets)));
    }

}
