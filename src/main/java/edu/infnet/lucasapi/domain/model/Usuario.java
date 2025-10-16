package edu.infnet.lucasapi.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Usuario extends Pessoa {

    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-pets")
    private List<Pet> pets;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-avistamentos")
    private List<Avistamento> avistamentos;

    @OneToMany(mappedBy = "usuarioDestinatario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("usuario-notificacoes")
    private List<Notificacao> notificacoes;

    @Override
    public String toString() {
        return "Usuario {" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                '}';
    }
}
