package edu.infnet.lucasapi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes",
        indexes = {
                @Index(name = "ix_notificacoes_usuario", columnList = "usuario_destinatario_id"),
                @Index(name = "ix_notificacoes_avistamento", columnList = "avistamento_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Notificacao extends BaseEntity{

    @Column(nullable = false, length = 255)
    private String mensagem;

    @Column(nullable = false)
    private Boolean lida;

    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_destinatario_id", nullable = false)
    @JsonBackReference("usuario-notificacoes")
    private Usuario usuarioDestinatario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avistamento_id", nullable = false)
    @JsonBackReference("avistamento-notificacoes")
    private Avistamento avistamento;
}
