package at.campus.ads.persistence;

import at.campus.ads.domain.User;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> findById(int id);

    List<T> findAll();

    Integer save(T t);

    void update(T t);

    void delete(T t);
}