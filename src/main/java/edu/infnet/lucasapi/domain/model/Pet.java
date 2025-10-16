package edu.infnet.lucasapi.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.infnet.lucasapi.domain.enums.StatusPet;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 80)
    private String nome;

    @Column(nullable = false, length = 50)
    private String especie;

    @Column(length = 50)
    private String raca;

    @Column(length = 50)
    private String cor;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "foto_url", length = 255)
    private String fotoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusPet status;

    @Column(name = "desaparecido_em")
    private LocalDate desaparecidoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference("usuario-pets")
    private Usuario usuario;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("pet-avistamentos")
    private List<Avistamento> avistamentos;

    @Override
    public String toString() {
        return "Pet {" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", especie='" + especie + '\'' +
                ", cor='" + cor + '\'' +
                ", descricao='" + descricao + '\'' +
                ", status=" + status +
                ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
                '}';
    }

}
