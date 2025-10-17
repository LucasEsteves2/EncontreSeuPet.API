package edu.infnet.lucasapi.controller.dto.response;

import edu.infnet.lucasapi.domain.model.Usuario;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UsuarioResponseDto {

    private Long id;
    private String nome;
    private String email;
    private String telefone;

    private List<PetResponseDto> pets;
    private List<AvistamentoResponseDto> avistamentos;
    private List<NotificacaoResponseDto> notificacoes;

    public static UsuarioResponseDto fromEntity(Usuario u) {
        if (u == null) return null;

        return UsuarioResponseDto.builder()
                .id(u.getId())
                .nome(u.getNome())
                .email(u.getEmail())
                .telefone(u.getTelefone())
                .pets(u.getPets() != null
                        ? PetResponseDto.fromEntities(u.getPets())
                        : null)
                .avistamentos(u.getAvistamentos() != null
                        ? AvistamentoResponseDto.fromEntities(u.getAvistamentos())
                        : null)
                .notificacoes(u.getNotificacoes() != null
                        ? NotificacaoResponseDto.fromEntities(u.getNotificacoes())
                        : null)
                .build();
    }

    public static List<UsuarioResponseDto> fromEntities(List<Usuario> usuarios) {
        if (usuarios == null) return null;
        return usuarios.stream()
                .map(UsuarioResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
