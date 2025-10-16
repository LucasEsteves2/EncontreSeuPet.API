package edu.infnet.lucasapi.service;

import edu.infnet.lucasapi.domain.model.Usuario;
import edu.infnet.lucasapi.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends BaseCrudService<Usuario, Long> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}

