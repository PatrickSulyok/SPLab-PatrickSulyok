package ro.uvt.sp.lab1;

import java.util.Objects;

public class Author {
    private final String name;
    private final String surname;

    public Author(String name) {
        this(name, "");
    }

    public Author(String name, String surname) {
        this.name = Objects.requireNonNull(name, "name");
        this.surname = Objects.requireNonNull(surname, "surname");
    }

    public void print() {
        System.out.println("Author: " + name + (surname.isBlank() ? "" : " " + surname));
    }
}
