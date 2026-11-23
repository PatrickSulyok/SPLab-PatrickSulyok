
package ro.uvt.sp.lab1;

import jakarta.persistence.Entity;

@Entity
public class TableOfContents extends Element {

    public TableOfContents() {
    }

    @Override
    public void render() {
        System.out.println("Table of Contents");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
