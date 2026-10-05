package ro.uvt.sp.lab1;

/** Common component in the book's Composite structure. */
public abstract class Element {
    private Section parent;

    public final Section getParent() {
        return parent;
    }

    // Ownership is changed only by Section.add/remove, not by callers.
    final void setParent(Section parent) {
        this.parent = parent;
    }

    public abstract void print();

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
