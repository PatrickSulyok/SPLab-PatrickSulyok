package ro.uvt.sp.lab1;

import java.util.Objects;

public class ImageProxy extends Element {
    private final String url;
    private Image realImage;

    public ImageProxy(String url) {
        this.url = Objects.requireNonNull(url, "url");
    }

    @Override
    public void print() {
        if (realImage == null) {
            realImage = new Image(url);
        }

        realImage.print();
    }
}