
package ro.uvt.sp.lab1;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Book extends Section {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @OrderColumn(name = "author_position")
    private List<Author> authors = new ArrayList<>();

    protected Book() {
    }

    public Book(String title) {
        super(title);
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void addAuthor(Author author) {
        authors.add(Objects.requireNonNull(author, "author"));
    }

    public void addContent(Element element) {
        add(element);
    }

    @Override
    public void render() {
        System.out.println("Book: " + getTitle());
        System.out.println();
        System.out.println("Authors:");

        for (Author author : authors) {
            author.print();
        }

        System.out.println();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        super.acceptChildren(visitor);
    }
}
