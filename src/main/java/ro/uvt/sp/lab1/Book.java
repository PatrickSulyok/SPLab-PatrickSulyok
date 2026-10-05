package ro.uvt.sp.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book extends Section {
    private final List<Author> authors = new ArrayList<>();

    public Book(String title) {
        super(title);
    }

    public void addAuthor(Author author) {
        authors.add(Objects.requireNonNull(author, "author"));
    }

    public void addContent(Element element) {
        add(element);
    }

    @Override
    public void print() {
        System.out.println("Book: " + getTitle());
        System.out.println();
        System.out.println("Authors:");
        for (Author author : authors) {
            author.print();
        }
        System.out.println();
        super.print();
    }

    @Override
    protected void printTitle() {
        // Book.print already prints the title before the authors.
    }
}
