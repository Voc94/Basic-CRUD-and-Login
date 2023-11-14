package model;

import model.builder.BookBuilderInterface;

import java.time.LocalDate;

public class EBook implements BookInterface {
    private Long id;

    private String author;

    private String title;

    private LocalDate publishedDate;

    private String format;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getFormat(){
        return format;
    }
    public void setFormat(String format){
        this.format = format;
    }
    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
    @Override
    public String toString(){
        return String.format("Id: %d | Title: %s | Author: %s | Format: %s |Date: %s", this.id, this.title, this.author,this.format, this.publishedDate);
    }
}
