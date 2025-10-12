package edu.infnet.lucasapi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "notificacoes",
        indexes = {
                @Index(name = "ix_notificacoes_usuario", columnList = "usuario_id")
        })
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 256)
    private String mensagem;

    @Column(nullable = false, name = "data_envio")
    private LocalDateTime dataEnvio;

    @Column(nullable = false)
    private Boolean lida;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_notificacao_usuario"))
    @JsonBackReference("usuario-notificacoes")
    private Usuario usuario;
}