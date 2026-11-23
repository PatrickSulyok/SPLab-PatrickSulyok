
package ro.uvt.sp.lab1;

import jakarta.persistence.Entity;
import java.util.Objects;

@Entity
@jakarta.persistence.Table(name = "book_tables")
public class Table extends Element {

    private String title;

    protected Table() {
    }

    public Table(String title) {
        this.title = Objects.requireNonNull(title, "title");
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void render() {
        System.out.println("Table with Title: " + title);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
