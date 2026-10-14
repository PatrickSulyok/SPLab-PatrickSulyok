package ro.uvt.sp.lab1;

public class TableOfContents extends Element {

    @Override
    public void render() {
        System.out.println("Table of Contents");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}