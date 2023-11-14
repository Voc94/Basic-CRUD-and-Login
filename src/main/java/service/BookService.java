package service;

import model.Book;
import model.BookInterface;

import java.util.List;

public interface BookService {

    List<BookInterface> findAll();

    BookInterface findById(Long id);

    boolean save(BookInterface book);

    int getAgeOfBook(Long id);
}
