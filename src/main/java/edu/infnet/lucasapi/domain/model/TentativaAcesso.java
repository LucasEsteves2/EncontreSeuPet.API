package edu.infnet.lucasapi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tentativas_acesso",
        indexes = {
                @Index(name = "ix_tentativas_usuario", columnList = "usuario_id"),
                @Index(name = "ix_tentativas_site", columnList = "site_aposta_id"),
                @Index(name = "ix_tentativas_data", columnList = "data_tentativa")
        })
public class TentativaAcesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "data_tentativa", nullable = false)
    private LocalDateTime dataTentativa;

    @Column(nullable = false)
    private Boolean bloqueado;

    @Column(name = "ip_origem", length = 50)
    private String ipOrigem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false,  foreignKey = @ForeignKey(name = "fk_tentativa_usuario"))
    @JsonBackReference("usuario-tentativas")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_aposta_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_tentativa_site"))
    private SiteAposta siteAposta;
}
