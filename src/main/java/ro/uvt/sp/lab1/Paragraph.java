package ro.uvt.sp.lab1;

import java.util.Objects;

public class Paragraph extends Element {
    private final String text;
    private AlignStrategy alignStrategy;
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
    public void print() {
        if (alignStrategy == null) {
            System.out.println("Paragraph: " + text);
        } else {
            alignStrategy.render(this, new Context());
        }

    }
}
