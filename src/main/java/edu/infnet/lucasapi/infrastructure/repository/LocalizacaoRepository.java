package edu.infnet.lucasapi.infrastructure.repository;

import edu.infnet.lucasapi.domain.model.Localizacao;
import edu.infnet.lucasapi.domain.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalizacaoRepository extends JpaRepository<Localizacao, Long>, JpaSpecificationExecutor<Localizacao> {
    List<Localizacao> findByTipo(String tipo);
}
