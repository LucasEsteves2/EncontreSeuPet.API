package edu.infnet.lucasapi.infrastructure.repository;

import edu.infnet.lucasapi.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    boolean existsByEmail(String email);

    List<Usuario> findByNomeContainingIgnoreCase(String nome);
}
