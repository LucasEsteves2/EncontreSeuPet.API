package edu.infnet.lucasapi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "avistamentos",
        indexes = {
                @Index(name = "ix_avistamento_pet", columnList = "pet_id"),
                @Index(name = "ix_avistamento_usuario", columnList = "usuario_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Avistamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "foto_url", length = 255)
    private String fotoUrl;

    @Column(name = "data_avistamento", nullable = false)
    private LocalDateTime dataAvistamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    @JsonBackReference("pet-avistamentos")
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuario-avistamentos")
    private Usuario usuario;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "localizacao_id")
    private Localizacao localizacao;

    @OneToMany(mappedBy = "avistamento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("avistamento-notificacoes")
    private List<Notificacao> notificacoes;
}
