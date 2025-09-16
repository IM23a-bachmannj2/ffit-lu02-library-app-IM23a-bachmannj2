package ch.bzz;

public class Book {
    private int id;
    private String isbn;
    private String title;
    private String author;
    private int publicationYear;

    public Book(int id, String isbn, String title, String author, int publicationYear) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public int getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    @Override
    public String toString() {
        return id + ": " + title + " by " + author + " (" + publicationYear + ")";
    }
}