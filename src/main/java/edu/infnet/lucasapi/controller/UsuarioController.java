package edu.infnet.lucasapi.controller;

import edu.infnet.lucasapi.domain.model.Usuario;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @PostMapping
    public String salvar(Usuario usuario) {
        return "fala comigo";
    }
}
