package edu.infnet.lucasapi.repository;

import edu.infnet.lucasapi.domain.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByUsuarioDestinatario_Id(Long usuarioId);

    List<Notificacao> findByUsuarioDestinatario_IdAndLidaFalse(Long usuarioId);
}
