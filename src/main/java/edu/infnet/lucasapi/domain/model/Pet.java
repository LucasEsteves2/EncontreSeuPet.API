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
@EqualsAndHashCode(callSuper = true)
public class Pet extends BaseEntity {

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

    @Column(name = "idade")
    private Integer idade;

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
                "id=" + getId() +
                ", nome='" + nome + '\'' +
                ", especie='" + especie + '\'' +
                ", cor='" + cor + '\'' +
                ", idade=" + idade +
                ", descricao='" + descricao + '\'' +
                ", status=" + status +
                ", usuario=" + (usuario != null ? usuario.getNome() : "null") +
                '}';
    }

}
