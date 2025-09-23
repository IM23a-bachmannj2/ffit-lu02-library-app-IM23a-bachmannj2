package ch.bzz.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.bzz.model.Book;
import ch.bzz.db.Database;
import java.sql.Statement;

public class GetBooks {
    private static final Logger log = LoggerFactory.getLogger(GetBooks.class);

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT id, isbn, title, author, publication_year FROM books ORDER BY id";

        try (Connection con = Database.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("publication_year");

                Book book = new Book(id, isbn, title, author, year);
                books.add(book);
            }

        } catch (SQLException e) {
            log.error("Failed to SELECT books from Database", e);
        }

        return books;
    }

    public static List<Book> getBooks(int count) {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT id, isbn, title, author, publication_year FROM books ORDER BY id LIMIT ?";

        try (Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, count);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String isbn = rs.getString("isbn");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    int year = rs.getInt("publication_year");

                    Book book = new Book(id, isbn, title, author, year);
                    books.add(book);
                }
            }

        } catch (SQLException e) {
            log.error("Failed to SELECT books from Database", e);
        }

        return books;
    }
}
