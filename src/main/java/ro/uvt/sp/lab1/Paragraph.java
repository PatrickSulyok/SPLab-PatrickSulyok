
package ro.uvt.sp.lab1;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Paragraph extends Element {

    @Column(length = 10000)
    private String text;

    @Transient
    private AlignStrategy alignStrategy;

    protected Paragraph() {
    }

    public Paragraph(String text) {
        this.text = Objects.requireNonNull(text, "text");
    }

    public String getText() {
        return text;
    }

    public void setAlignStrategy(AlignStrategy alignStrategy) {
        this.alignStrategy = alignStrategy;
    }

    @Override
    public void render() {
        if (alignStrategy == null) {
            System.out.println("Paragraph: " + text);
        } else {
            alignStrategy.render(this, new Context());
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
