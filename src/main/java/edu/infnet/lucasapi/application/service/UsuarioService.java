package edu.infnet.lucasapi.application.service;

import edu.infnet.lucasapi.domain.exception.UsuarioException;
import edu.infnet.lucasapi.domain.model.Usuario;
import edu.infnet.lucasapi.infrastructure.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService extends BaseCrudService<Usuario, Long> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        super(usuarioRepository);
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario buscarPorId(Long id) {
        var usuario = super.buscarPorId(id);

        if (usuario == null)
            throw UsuarioException.naoEncontrado(id);

        return usuario;
    }

    public List<Usuario> buscarUsuarios(String nomeContains, String emailContains) {

        if (nomeContains != null && emailContains != null) {
            return usuarioRepository.findByNomeContainingIgnoreCaseAndEmailContainingIgnoreCase(nomeContains, emailContains);
        }
        else if (nomeContains != null) {
            return usuarioRepository.findByNomeContainingIgnoreCase(nomeContains);
        }
        else if (emailContains != null) {
            return usuarioRepository.findByEmailContainingIgnoreCase(emailContains);
        }

        return List.of();
    }



    public Page<Usuario> buscarComFiltros(Pageable pageable, String nome, String email, String telefone) {
        var filtros = criarFiltros(nome, email, telefone);
        return usuarioRepository.findAll(filtros, pageable);
    }

    private Specification<Usuario> criarFiltros(String nome, String email, String telefone) {
        Specification<Usuario> filtros = (root, query, cb) -> cb.conjunction();

        if (nome != null && !nome.isBlank())
            filtros = filtros.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));

        if (email != null && !email.isBlank())
            filtros = filtros.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));

        if (telefone != null && !telefone.isBlank())
            filtros = filtros.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("telefone")), "%" + telefone.toLowerCase() + "%"));

        return filtros;
    }

}
