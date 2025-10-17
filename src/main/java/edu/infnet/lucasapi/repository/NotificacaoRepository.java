package edu.infnet.lucasapi.repository;

import edu.infnet.lucasapi.domain.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>, JpaSpecificationExecutor<Notificacao> {

}
