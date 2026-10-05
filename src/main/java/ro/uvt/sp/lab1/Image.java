package ro.uvt.sp.lab1;

import java.util.Objects;

public class Image extends Element {
    private final String url;

    public Image(String url) {
        this.url = Objects.requireNonNull(url, "url");
    }

    @Override
    public void print() {
        System.out.println("Image with name:" + url);
    }
}
