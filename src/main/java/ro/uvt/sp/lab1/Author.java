
package ro.uvt.sp.lab1;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    protected Author() {
    }

    public Author(String name) {
        this(name, "");
    }

    public Author(String name, String surname) {
        this.name = Objects.requireNonNull(name, "name");
        this.surname = Objects.requireNonNull(surname, "surname");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void print() {
        System.out.println("Author: " + name + (surname.isBlank() ? "" : " " + surname));
    }
}
