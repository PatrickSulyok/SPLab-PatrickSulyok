package ro.uvt.sp.lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Section extends Element {
    private final String title;
    private final List<Element> children = new ArrayList<>();

    public Section(String title) {
        this.title = Objects.requireNonNull(title, "title");
    }

    public String getTitle() {
        return title;
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
        // Compare by identity so only the actual owned instance is detached.
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
    public void print() {
        printTitle();
        for (Element child : children) {
            child.print();
        }
    }

    protected void printTitle() {
        System.out.println(title);
    }
}
