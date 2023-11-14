package repository;

import model.Book;
import model.BookInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMock implements BookRepository{

    private List<BookInterface> books;

    public BookRepositoryMock(){
        books = new ArrayList<>();
    }

    @Override
    public List<BookInterface> findAll() {
        return books;
    }

    @Override
    public Optional<BookInterface> findById(Long id) {
        return books.parallelStream()
                .filter(it -> it.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean save(BookInterface book) {
        return books.add(book);
    }

    @Override
    public void removeAll() {
        books.clear();
    }
}
