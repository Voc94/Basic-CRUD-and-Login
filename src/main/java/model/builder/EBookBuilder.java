package model.builder;

import model.BookInterface;
import model.EBook;

import java.time.LocalDate;

public class EBookBuilder implements BookBuilderInterface<EBook, EBookBuilder> {
    private EBook book;

    public EBookBuilder() {
        this.book = new EBook();
    }

    @Override
    public EBookBuilder setId(Long id) {
        book.setId(id);
        return this;
    }

    @Override
    public EBookBuilder setAuthor(String author) {
        book.setAuthor(author);
        return this;
    }

    @Override
    public EBookBuilder setTitle(String title) {
        book.setTitle(title);
        return this;
    }

    @Override
    public EBookBuilder setPublishedDate(LocalDate publishedDate) {
        book.setPublishedDate(publishedDate);
        return this;
    }

    public EBookBuilder setFormat(String format) {
        book.setFormat(format);
        return this;
    }

    @Override
    public BookInterface build() {
        return book;
    }
}
