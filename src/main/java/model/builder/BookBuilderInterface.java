package model.builder;

import model.BookInterface;

import java.time.LocalDate;

public interface BookBuilderInterface<T, E> {
    BookBuilderInterface<T, E> setId(Long id);

    BookBuilderInterface<T, E> setAuthor(String author);

    BookBuilderInterface<T, E> setTitle(String title);

    BookBuilderInterface<T, E> setPublishedDate(LocalDate publishedDate);
    BookInterface build();

}
