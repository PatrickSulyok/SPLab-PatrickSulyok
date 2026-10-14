package ro.uvt.sp.lab1;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Image extends Element {
    private final String url;

    public Image(String url) {
        this.url = Objects.requireNonNull(url, "url");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void render() {
        System.out.println("Image with name:" + url);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}