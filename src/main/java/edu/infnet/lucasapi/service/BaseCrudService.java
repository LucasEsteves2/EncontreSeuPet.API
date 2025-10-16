package edu.infnet.lucasapi.service;

import edu.infnet.lucasapi.interfaces.CrudService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseCrudService<T, ID> implements CrudService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected BaseCrudService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public Page<T> listarTodos(Pageable pageable) {

        if (pageable == null) {
            List<T> all = repository.findAll();
            return new PageImpl<>(all);
        }

        return repository.findAll(pageable);
    }

    @Override
    public T criar(T entidade) {
        return repository.save(entidade);
    }

    @Override
    public T buscarPorId(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Registro não encontrado para o ID: %s", id)
                ));
    }

    @Override
    public void excluir(ID id) {
        T entidade = buscarPorId(id);
        repository.delete(entidade);
    }
}