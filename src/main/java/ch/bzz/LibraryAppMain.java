package ch.bzz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.sql.Statement;

public class LibraryAppMain {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            Properties configProps = new Properties();
            configProps.load(new FileInputStream("src/main/config.properties"));

            URL = configProps.getProperty("URL");
            USER = configProps.getProperty("USER");
            PASSWORD = configProps.getProperty("PASSWORD", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT id, isbn, title, author, publication_year FROM books";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
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

    public static int insertBooksIntoDB(List<Book> books) {
        int count = 0;
        String sql = "INSERT INTO books (id, isbn, title, author, publication_year) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
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

    public static List<Book> importBooks(String filePath) {
        List<Book> books = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath), "UTF-8")) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // skip header
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split("\t");

                int id = Integer.parseInt(fields[0]);
                String isbn = fields[1];
                String title = fields[2];
                String authors = fields[3];
                int publication_year = Integer.parseInt(fields[4]);

                books.add(new Book(id, isbn, title, authors, publication_year));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        String input = "";
        String[] commands = { "help", "quit", "listBooks", "importBooks data/books.tsv" };

        while (!"quit".equals(input)) {
            System.out.println("Enter command");
            input = myObj.nextLine();

            if ("help".equals(input)) {
                System.out.println("Verfügbare Befehle: help, quit, listBooks, importBooks");
            } else if ("listBooks".equals(input)) {
                List<Book> books = getAllBooks();
                for (Book book : books) {
                    System.out.println(book);
                }
            } else if (input.startsWith("importBooks")) {
                String[] parts = input.split(" ", 2);
                if (parts.length < 2) {
                    System.out.println("Bitte Dateipfad angeben: importBooks <FILE_PATH>");
                } else {
                    String filePath = parts[1];
                    List<Book> books = importBooks(filePath);
                    insertBooksIntoDB(books);
                    System.out.println(books.size() + " Bücher importiert.");
                }
            } else if (!Arrays.asList(commands).contains(input)) {
                System.out.println(input + " Eingabe nicht als Befehl erkannt");
            }
        }
        myObj.close();
    }
}
