package ch.bzz.io;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ch.bzz.model.Book;
import ch.bzz.db.Database;
import java.sql.Statement;

public class GetBooks {
    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT id, isbn, title, author, publication_year FROM books";

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
            e.printStackTrace();
        }

        return books;
    }
}
