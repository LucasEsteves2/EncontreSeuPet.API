package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.domain.enums.StatusPet;
import edu.infnet.lucasapi.domain.model.Pet;
import edu.infnet.lucasapi.domain.services.PetService;
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
    public ResponseEntity<Pet> criar(@RequestBody Pet pet) {
        petService.criar(pet);
        return ResponseEntity.created(URI.create("/pets/" + pet.getId())).body(pet);
    }

    @GetMapping
    public ResponseEntity<List<Pet>> listarTodos() {
        return ResponseEntity.ok(petService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(petService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        petService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pet>> buscarPorStatus(@PathVariable StatusPet status) {
        return ResponseEntity.ok(petService.buscarPorStatus(status));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pet>> buscarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(petService.buscarPorUsuario(usuarioId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Pet>> buscarPorNome(@RequestParam("nome") String nome) {
        return ResponseEntity.ok(petService.buscarPorNome(nome));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> contarTodos() {
        return ResponseEntity.ok(petService.contarTodos());
    }
}
