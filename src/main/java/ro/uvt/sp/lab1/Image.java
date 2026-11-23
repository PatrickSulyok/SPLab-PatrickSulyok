
package ro.uvt.sp.lab1;

import jakarta.persistence.Entity;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Entity
public class Image extends Element {

    private String url;

    protected Image() {
    }

    public Image(String url) {
        this.url = Objects.requireNonNull(url, "url");

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getUrl() {
        return url;
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
