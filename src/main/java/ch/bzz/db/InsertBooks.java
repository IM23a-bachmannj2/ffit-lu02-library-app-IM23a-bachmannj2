package ch.bzz.db;

import ch.bzz.model.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsertBooks {
    private static final Logger log = LoggerFactory.getLogger(InsertBooks.class);

    public static int insertBooksintoDB(List<Book> books) {
        int count = 0;
        String sql = """
                INSERT INTO books (id, isbn, title, author, publication_year)
                VALUES (?, ?, ?, ?, ?)
                ON CONFLICT (id) DO UPDATE
                SET isbn = EXCLUDED.isbn,
                    title = EXCLUDED.title,
                    author = EXCLUDED.author,
                    publication_year = EXCLUDED.publication_year
                """;

        try (Connection con = Database.getConnection();
                java.sql.PreparedStatement pstmt = con.prepareStatement(sql)) {

            for (Book book : books) {
                pstmt.setInt(1, book.getId());
                pstmt.setString(2, book.getIsbn());
                pstmt.setString(3, book.getTitle());
                pstmt.setString(4, book.getAuthor());
                pstmt.setInt(5, book.getPublicationYear());

                pstmt.addBatch();
                count++;
            }
            var changedRows = pstmt.executeBatch();
            log.info(Arrays.toString(changedRows));

        } catch (SQLException e) {
            log.error("Failed to insert books", e);
        }
        return count;
    }
}