import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.AudioBook;
import model.Book;
import model.BookInterface;
import model.EBook;
import model.builder.AudioBookBuilder;
import model.builder.BookBuilder;
import model.builder.EBookBuilder;
import repository.*;
import service.BookService;
import service.BookServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args){
        BookRepository bookRepository =new BookRepositoryCacheDecorator(
                new BookRepositoryMySQL(DatabaseConnectionFactory.getConnectionWrapper(true).getConnection()),
                new Cache<>()
        );

        BookService bookService = new BookServiceImpl(bookRepository);

        Book book = new BookBuilder()
                .setTitle("Morometii")
                .setAuthor("Marin Preda")
                .setPublishedDate(LocalDate.of(1955, 1, 1))
                .build();
        // Save book boolean
        boolean savedRes1 = bookService.save(book);
        System.out.println("Book saved: " + savedRes1);


        // Find the saved book
        Long savedBookId1 = book.getId();
        System.out.println("Finding book with ID: " + savedBookId1);
        System.out.println(bookService.findById(savedBookId1));

        EBook eBook = (EBook) new EBookBuilder()
                .setTitle("Cracking Codes and Cryptograms for Dummies")
                .setAuthor("Denise Sutherland")
                .setFormat("PDF")
                .setPublishedDate(LocalDate.of(2020, 1, 1))
                .build();
        // save ebook boolean
        boolean savedRes2 = bookService.save(eBook);
        System.out.println("Book saved: " + savedRes2);


        // Find the saved ebook
        Long savedBookId2 = eBook.getId();
        System.out.println("Finding book with ID: " + savedBookId2);
        System.out.println(bookService.findById(savedBookId2));


        AudioBook audioBook = (AudioBook) new AudioBookBuilder()
                .setTitle("Crime and Punishment")
                .setAuthor("James Mason")
                .setRunTime(55)
                .setPublishedDate(LocalDate.of(2022, 12, 1))
                .build();
        // save ebook boolean
        boolean savedRes3 = bookService.save(audioBook);
        System.out.println("Book saved: " + savedRes3);


        // Find the saved ebook
        Long savedBookId3 = audioBook.getId();
        System.out.println("Finding book with ID: " + savedBookId3);
        System.out.println(bookService.findById(savedBookId3));

        // Print all books
        List<BookInterface> allBooks = bookService.findAll();
        System.out.println("All books in the repository:");
        for (BookInterface bookIndex : allBooks) {
            System.out.println(bookIndex);
            System.out.println();
        }






    }
}
