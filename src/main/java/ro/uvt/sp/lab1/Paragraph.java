package ro.uvt.sp.lab1;

import java.util.Objects;

public class Paragraph extends Element {
    private final String text;

    public Paragraph(String text) {
        this.text = Objects.requireNonNull(text, "text");
    }

    @Override
    public void print() {
        System.out.println("Paragraph: " + text);
    }
}
