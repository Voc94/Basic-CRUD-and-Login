package repository;

import model.AudioBook;
import model.Book;
import model.BookInterface;
import model.EBook;
import model.builder.AudioBookBuilder;
import model.builder.BookBuilder;
import model.builder.EBookBuilder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryMySQL implements BookRepository {

    private final Connection connection;

    public BookRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<BookInterface> findAll() {
        String sql = "SELECT * FROM book;";

        List<BookInterface> books = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                books.add(getBookFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public Optional<BookInterface> findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        Optional<BookInterface> book = Optional.empty();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                book = Optional.of(getBookFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return book;
    }

    @Override
    public boolean save(BookInterface book) {
        String sql = "INSERT INTO book (author, title, publishedDate, runTime, format) VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(book.getPublishedDate()));
            // Set additional parameters based on the type of book
            if (book instanceof AudioBook) {
                preparedStatement.setInt(4, ((AudioBook) book).getRunTime());
                preparedStatement.setNull(5, Types.VARCHAR);
            } else if (book instanceof EBook) {
                preparedStatement.setNull(4, Types.INTEGER);
                preparedStatement.setString(5, ((EBook) book).getFormat());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
                preparedStatement.setNull(5, Types.VARCHAR);
            }

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long generatedId = generatedKeys.getLong(1);
                        book.setId(generatedId);
                    }
                }
            }

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void removeAll() {
        String sql = "DELETE FROM book WHERE id >= 0;";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private BookInterface getBookFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        LocalDate publishedDate = resultSet.getDate("publishedDate").toLocalDate();

        if (resultSet.getInt("runTime") > 0) {
            return new AudioBookBuilder()
                    .setId(id)
                    .setTitle(title)
                    .setAuthor(author)
                    .setPublishedDate(publishedDate)
                    .setRunTime(resultSet.getInt("runTime"))
                    .build();
        } else if (resultSet.getString("format") != null) {
            return new EBookBuilder()
                    .setId(id)
                    .setTitle(title)
                    .setAuthor(author)
                    .setPublishedDate(publishedDate)
                    .setFormat(resultSet.getString("format"))
                    .build();
        } else {
            return new BookBuilder()
                    .setId(id)
                    .setTitle(title)
                    .setAuthor(author)
                    .setPublishedDate(publishedDate)
                    .build();
        }
    }

}
