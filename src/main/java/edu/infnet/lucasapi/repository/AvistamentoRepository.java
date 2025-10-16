package edu.infnet.lucasapi.repository;

import edu.infnet.lucasapi.domain.model.Avistamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvistamentoRepository extends JpaRepository<Avistamento, Long> {

    List<Avistamento> findByPetId(Long petId);

    List<Avistamento> findByUsuarioId(Long usuarioId);
}
