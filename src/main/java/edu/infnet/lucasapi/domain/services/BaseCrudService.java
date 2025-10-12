package edu.infnet.lucasapi.domain.services;

import edu.infnet.lucasapi.interfaces.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseCrudService<T, ID> implements CrudService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected BaseCrudService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public void criar(T entidade) {
        repository.save(entidade);
    }

    @Override
    public T buscarPorId(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entidade não encontrada com ID: " + id));
    }

    @Override
    public List<T> listarTodos() {
        return repository.findAll();
    }

    @Override
    public void excluir(ID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Não foi possível excluir: ID não encontrado.");
        }
        repository.deleteById(id);
    }
}