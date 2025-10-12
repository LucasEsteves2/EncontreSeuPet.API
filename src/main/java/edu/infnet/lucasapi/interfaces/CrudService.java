package edu.infnet.lucasapi.interfaces;
import java.util.List;

public interface CrudService<T, ID> {
    void incluir(T entidade);
    T buscarPorId(ID id);
    List<T> listarTodos();
    void excluir(ID id);
}
