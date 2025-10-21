package edu.infnet.lucasapi.controller.dto.request;

import edu.infnet.lucasapi.domain.model.Usuario;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioRequestDto {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve estar em um formato válido.")
    private String email;

    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;


    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
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
