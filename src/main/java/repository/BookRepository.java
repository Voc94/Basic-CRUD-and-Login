package repository;

import model.BookInterface;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<BookInterface> findAll();

    Optional<BookInterface> findById(Long id);

    boolean save(BookInterface book);

    void removeAll();
}
