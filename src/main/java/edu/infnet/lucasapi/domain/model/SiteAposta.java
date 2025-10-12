package edu.infnet.lucasapi.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "sites_aposta",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_sites_aposta_dominio", columnNames = "dominio")
        })
public class SiteAposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String dominio;

    @Column(length = 50)
    private String categoria;

    @Column(nullable = false)
    private Boolean ativo;
}
