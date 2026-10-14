package ro.uvt.sp.lab1;

public class RenderContentVisitor implements Visitor {

    @Override
    public void visit(Book book) {
        book.render();
    }

    @Override
    public void visit(Section section) {
        section.render();
    }

    @Override
    public void visit(Paragraph paragraph) {
        paragraph.render();
    }

    @Override
    public void visit(Image image) {
        image.render();
    }

    @Override
    public void visit(ImageProxy imageProxy) {
        imageProxy.render();
    }

    @Override
    public void visit(Table table) {
        table.render();
    }

    @Override
    public void visit(TableOfContents tableOfContents) {
        tableOfContents.render();
    }
}