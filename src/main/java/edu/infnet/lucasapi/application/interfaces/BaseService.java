package edu.infnet.lucasapi.application.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService<T, ID> {
    T criar(T entidade);

    T buscarPorId(ID id);

    T atualizar(ID id, T entidadeAtualizada);

    Page<T> listarTodos(Pageable pageable);

    void excluir(ID id);
}
