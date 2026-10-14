package ro.uvt.sp.lab1;

public abstract class Element {
    private Section parent;

    public final Section getParent() {
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