
package ro.uvt.sp.lab1;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Section parent;

    public Long getId() {
        return id;
    }

    @JsonIgnore
    public Section getParent() {
        return parent;
    }

    final void setParent(Section parent) {
        this.parent = parent;
    }

    public abstract void render();

    public abstract void accept(Visitor visitor);

    public void add(Element element) {
        throw new UnsupportedOperationException("A leaf element cannot contain children.");
    }

    public void remove(Element element) {
        throw new UnsupportedOperationException("A leaf element cannot contain children.");
    }

    public Element get(int index) {
        throw new UnsupportedOperationException("A leaf element has no children.");
    }
}
