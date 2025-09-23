package ch.bzz;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ch.bzz.model.Book;
import ch.bzz.io.GetBooks;
import ch.bzz.io.ImportTsv;
import ch.bzz.db.InsertBooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryAppMain {
    private static final Logger log = LoggerFactory.getLogger(LibraryAppMain.class);

    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        String input = "";
        String[] commands = { "help", "quit", "listBooks", "importBooks" };

        while (!"quit".equals(input)) {
            System.out.println("Enter command");
            input = myObj.nextLine();

            if ("help".equals(input)) {
                System.out.println("Verfügbare Befehle: help, quit, listBooks, importBooks");
            } else if ("listBooks".equals(input)) {
                List<Book> books = GetBooks.getAllBooks();
                for (Book book : books) {
                    System.out.println(book);
                }
            } else if (input.startsWith("importBooks")) {
                String[] parts = input.split(" ", 2);
                if (parts.length < 2) {
                    System.out.println("Bitte Dateipfad angeben: importBooks <FILE_PATH>");
                } else {
                    String filePath = parts[1];
                    List<Book> books = ImportTsv.importBooks(filePath);
                    InsertBooks.insertBooksintoDB(books);
                    log.info(books.size() + " Bücher importiert.");
                    System.out.println(books.size() + " Bücher importiert.");
                }
            } else if (!Arrays.asList(commands).contains(input)) {
                log.info(input + " Eingabe nicht als Befehl erkannt");
                System.out.println(input + " Eingabe nicht als Befehl erkannt");
            }
        }
        myObj.close();
    }
}
