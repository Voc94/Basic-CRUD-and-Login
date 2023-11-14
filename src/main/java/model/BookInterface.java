package model;

import java.time.LocalDate;

public interface BookInterface {
    Long getId();

    String getAuthor();

    String getTitle();

    LocalDate getPublishedDate();
    void setId(Long id);
    String toString();
}
