package ch.bzz.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import ch.bzz.model.Book;

public class InsertBooks {
    public static int insertBooksintoDB(List<Book> books) {
        int count = 0;
        String sql = "INSERT INTO books (id, isbn, title, author, publication_year) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Database.getConnection();
                java.sql.PreparedStatement pstmt = con.prepareStatement(sql)) {

            for (Book book : books) {
                pstmt.setInt(1, book.getId());
                pstmt.setString(2, book.getIsbn());
                pstmt.setString(3, book.getTitle());
                pstmt.setString(4, book.getAuthor());
                pstmt.setInt(5, book.getPublicationYear());

                pstmt.executeUpdate();
                count++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}