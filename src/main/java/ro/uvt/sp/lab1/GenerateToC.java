package ro.uvt.sp.lab1;

public class GenerateToC implements Visitor {
    private final StringBuilder tableOfContents = new StringBuilder();

    @Override
    public void visit(Book book) {
        tableOfContents.append("Table of Contents").append(System.lineSeparator());
    }

    @Override
    public void visit(Section section) {
        int depth = 0;

        for (Section parent = section.getParent(); parent != null; parent = parent.getParent()) {
            if (!(parent instanceof Book)) {
                depth++;
            }
        }

        tableOfContents.append("  ".repeat(depth))
                .append(section.getTitle())
                .append(System.lineSeparator());
    }

    @Override
    public void visit(Paragraph paragraph) {
    }

    @Override
    public void visit(Image image) {
    }

    @Override
    public void visit(ImageProxy imageProxy) {
    }

    @Override
    public void visit(Table table) {
    }

    @Override
    public void visit(TableOfContents tableOfContents) {
    }

    public String getToC() {
        return tableOfContents.toString();
    }
}