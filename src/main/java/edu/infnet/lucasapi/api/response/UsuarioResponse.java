package edu.infnet.lucasapi.api.response;

import edu.infnet.lucasapi.domain.model.Usuario;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;
    private String telefone;

    private List<PetResponse> pets;
    private List<AvistamentoResponse> avistamentos;
    private List<NotificacaoResponse> notificacoes;

    public static UsuarioResponse fromEntity(Usuario u) {
        if (u == null) return null;

        return UsuarioResponse.builder()
                .id(u.getId())
                .nome(u.getNome())
                .email(u.getEmail())
                .telefone(u.getTelefone())
                .pets(u.getPets() != null
                        ? PetResponse.fromEntities(u.getPets())
                        : null)
                .avistamentos(u.getAvistamentos() != null
                        ? AvistamentoResponse.fromEntities(u.getAvistamentos())
                        : null)
                .notificacoes(u.getNotificacoes() != null
                        ? NotificacaoResponse.fromEntities(u.getNotificacoes())
                        : null)
                .build();
    }

    public static List<UsuarioResponse> fromEntities(List<Usuario> usuarios) {
        if (usuarios == null) return null;
        return usuarios.stream()
                .map(UsuarioResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
