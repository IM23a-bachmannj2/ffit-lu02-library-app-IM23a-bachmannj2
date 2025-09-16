package ch.bzz.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ch.bzz.model.Book;

public class ImportTsv {
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
}
