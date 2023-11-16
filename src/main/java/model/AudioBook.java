package model;

import java.time.LocalDate;

public class AudioBook implements BookInterface{
    private Long id;

    private String author;

    private String title;

    private LocalDate publishedDate;

    private int runTime;
    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Override
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
    public int getRunTime(){
        return runTime;
    }
    public void setRunTime(int runTime){
        this.runTime = runTime;
    }
    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
    @Override
    public String toString(){
        return String.format("Id: %d | Title: %s | Author: %s | RunTime: %s |Date: %s", this.id, this.title, this.author,this.runTime, this.publishedDate);
    }
}
