
package ro.uvt.sp.lab1;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Section extends Element {

    private String title;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @OrderColumn(name = "child_position")
    private List<Element> children = new ArrayList<>();

    protected Section() {
    }

    public Section(String title) {
        this.title = Objects.requireNonNull(title, "title");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title, "title");
    }

    public List<Element> getChildren() {
        return children;
    }

    @Override
    public void add(Element element) {
        Objects.requireNonNull(element, "element");

        for (Section ancestor = this; ancestor != null; ancestor = ancestor.getParent()) {
            if (ancestor == element) {
                throw new IllegalArgumentException("Adding this element would create a cycle.");
            }
        }

        if (element.getParent() != null) {
            throw new IllegalStateException("Element already belongs to a section or book.");
        }

        children.add(element);
        element.setParent(this);
    }

    @Override
    public void remove(Element element) {
        for (int index = 0; index < children.size(); index++) {
            if (children.get(index) == element) {
                children.remove(index);
                element.setParent(null);
                return;
            }
        }
    }

    @Override
    public Element get(int index) {
        return children.get(index);
    }

    @Override
    public void render() {
        System.out.println(title);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        acceptChildren(visitor);
    }

    protected void acceptChildren(Visitor visitor) {
        for (Element child : children) {
            child.accept(visitor);
        }
    }
}
