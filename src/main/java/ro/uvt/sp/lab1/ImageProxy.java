
package ro.uvt.sp.lab1;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import java.util.Objects;

@Entity
public class ImageProxy extends Element {

    private String url;

    @Transient
    private Image realImage;

    protected ImageProxy() {
    }

    public ImageProxy(String url) {
        this.url = Objects.requireNonNull(url, "url");
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void render() {
        if (realImage == null) {
            realImage = new Image(url);
        }

        realImage.render();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
