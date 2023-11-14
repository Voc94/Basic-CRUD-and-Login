package repository;

import model.Book;
import model.BookInterface;

import java.util.List;
import java.util.Optional;

public class BookRepositoryCacheDecorator extends BookRepositoryDecorator{
    private Cache<BookInterface> cache;

    public BookRepositoryCacheDecorator(BookRepository bookRepository, Cache<BookInterface> cache){
        super(bookRepository);
        this.cache = cache;
    }

    @Override
    public List<BookInterface> findAll() {
        if (cache.hasResult()){
           return cache.load();
        }

        List<BookInterface> books = decoratedRepository.findAll();
        cache.save(books);

        return books;
    }

    @Override
    public Optional<BookInterface> findById(Long id) {

        if (cache.hasResult()){
            return cache.load()
                    .stream()
                    .filter(it -> it.getId().equals(id))
                    .findFirst();
        }

        return decoratedRepository.findById(id);
    }

    @Override
    public boolean save(BookInterface book) {
        cache.invalidateCache();
        return decoratedRepository.save(book);
    }

    @Override
    public void removeAll() {
        cache.invalidateCache();
        decoratedRepository.removeAll();
    }
}
