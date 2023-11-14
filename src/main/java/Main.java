import database.DatabaseConnectionFactory;
import database.JDBConnectionWrapper;
import model.Book;
import model.builder.BookBuilder;
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
        boolean savedRes = bookService.save(book);
        System.out.println("Book saved: " + savedRes);

        // Find the saved book
        Long savedBookId = book.getId();
        System.out.println("Finding book with ID: " + savedBookId);
        System.out.println(bookService.findById(savedBookId));

        // Print all books
        System.out.println(bookService.findAll());
        System.out.println(bookService.findAll());
        List<Book> allBooks = bookService.findAll();
        System.out.println("All books in the repository:");
        for (Book bookIndex : allBooks) {
            System.out.println(bookIndex);
            System.out.println();
        }






    }
}
