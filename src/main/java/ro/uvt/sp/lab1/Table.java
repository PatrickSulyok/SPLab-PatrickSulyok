package ro.uvt.sp.lab1;

import java.util.Objects;

public class Table extends Element {
    private final String title;

    public Table(String title) {
        this.title = Objects.requireNonNull(title, "title");
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