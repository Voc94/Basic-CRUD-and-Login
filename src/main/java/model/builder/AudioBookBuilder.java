package model.builder;

import model.AudioBook;

import java.time.LocalDate;

public class AudioBookBuilder implements BookBuilderInterface<AudioBook, AudioBookBuilder> {
    private AudioBook book;

    public AudioBookBuilder() {
        this.book = new AudioBook();
    }

    public AudioBookBuilder setId(Long id) {
        book.setId(id);
        return this;
    }

    public AudioBookBuilder setAuthor(String author) {
        book.setAuthor(author);
        return this;
    }

    public AudioBookBuilder setTitle(String title) {
        book.setTitle(title);
        return this;
    }

    public AudioBookBuilder setPublishedDate(LocalDate publishedDate) {
        book.setPublishedDate(publishedDate);
        return this;
    }

    public AudioBookBuilder setRunTime(int runTime) {
        ((AudioBook) book).setRunTime(runTime);
        return this;
    }

    @Override
    public AudioBook build() {
        return (AudioBook) book;
    }
}