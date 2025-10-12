package edu.infnet.lucasapi.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(length = 20)
    private String telefone;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-pets")
    private List<Pet> pets;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-avistamentos")
    private List<Avistamento> avistamentos;

    @OneToMany(mappedBy = "usuarioDestinatario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-notificacoes")
    private List<Notificacao> notificacoes;
}
