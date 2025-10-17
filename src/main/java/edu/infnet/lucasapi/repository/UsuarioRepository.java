package edu.infnet.lucasapi.repository;

import edu.infnet.lucasapi.domain.model.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario> {
    @Override
    @EntityGraph(attributePaths = {"pets", "avistamentos", "notificacoes"})
    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByEmail(String email);
}