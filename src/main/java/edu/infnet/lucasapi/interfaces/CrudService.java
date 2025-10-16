package edu.infnet.lucasapi.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService<T, ID> {
    T criar(T entidade);

    T buscarPorId(ID id);

    Page<T> listarTodos(Pageable pageable);

    void excluir(ID id);
}
