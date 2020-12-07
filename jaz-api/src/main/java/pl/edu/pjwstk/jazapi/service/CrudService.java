package pl.edu.pjwstk.jazapi.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class CrudService<T extends DbEntity> {
    JpaRepository<T, Long> repository;
    public CrudService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    public List<T> getAll(int page,int size,String sort) {
        PageRequest pageRequest = PageRequest.of(page,size,Sort.by(sort));
        Iterable<T> items = repository.findAll(pageRequest);
        var itemList = new ArrayList<T>();

        items.forEach(itemList::add);
        return itemList;

    }

    public T getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        Optional<T> item = repository.findById(id);

        if (item.isPresent()) {
            repository.delete(item.orElseThrow());
        }
    }

    public abstract T createOrUpdate(T updateEntity);
}
