package edu.infnet.lucasapi.domain.model;

import edu.infnet.lucasapi.domain.enums.TipoLocalizacao;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "localizacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Localizacao extends BaseEntity {

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Embedded
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 30)
    private TipoLocalizacao tipo;

    @OneToOne(mappedBy = "localizacao", cascade = CascadeType.ALL)
    private Avistamento avistamento;

    @Override
    public String toString() {
        return "Localizacao {" +
                "id=" + getId() +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", tipo=" + tipo +
                ", endereco=" + (endereco != null ? endereco.toString() : "null") +
                '}';
    }

}
