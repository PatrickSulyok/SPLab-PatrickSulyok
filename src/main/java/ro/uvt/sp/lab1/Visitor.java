package ro.uvt.sp.lab1;

public interface Visitor {
    void visit(Book book);
    void visit(Section section);
    void visit(Paragraph paragraph);
    void visit(Image image);
    void visit(ImageProxy imageProxy);
    void visit(Table table);
    void visit(TableOfContents tableOfContents);
}
