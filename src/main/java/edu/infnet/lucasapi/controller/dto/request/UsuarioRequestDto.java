package edu.infnet.lucasapi.controller.dto.request;

import edu.infnet.lucasapi.domain.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRequestDto {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Email(message = "E-mail inválido.")
    @NotBlank(message = "O e-mail é obrigatório.")
    private String email;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

    public Usuario toEntity() {
        return Usuario.builder()
                .nome(nome)
                .email(email)
                .telefone(telefone)
                .senha(senha)
                .build();
    }
}
