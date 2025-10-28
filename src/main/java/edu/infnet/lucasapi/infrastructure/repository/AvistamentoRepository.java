package edu.infnet.lucasapi.infrastructure.repository;

import edu.infnet.lucasapi.domain.model.Avistamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvistamentoRepository extends JpaRepository<Avistamento, Long>, JpaSpecificationExecutor<Avistamento> {
    List<Avistamento> findByPetId(Long petId);
    List<Avistamento> findByUsuarioId(Long usuarioId);
}
