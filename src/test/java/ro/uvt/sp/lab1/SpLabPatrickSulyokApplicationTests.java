
package ro.uvt.sp.lab1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SpLabPatrickSulyokApplicationTests {

    @Test
    void mainExecutesVisitorExample() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try (PrintStream capture =
                     new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            SpLabPatrickSulyokApplication.main(new String[0]);
        } finally {
            System.setOut(original);
        }

        String result = output.toString(StandardCharsets.UTF_8)
                .replace("\r\n", "\n");

        assertTrue(result.contains("Book Statistics:"));
        assertTrue(result.contains("*** Number of images: 2"));
        assertTrue(result.contains("*** Number of tables: 1"));
        assertTrue(result.contains("*** Number of paragraphs: 5"));

        assertTrue(result.contains("Rendering content:"));
        assertTrue(result.contains("Capitolul 1"));
        assertTrue(result.contains("Paragraph: Paragraph 1"));
        assertTrue(result.contains("Image with name:ImageOne"));
        assertTrue(result.contains("Image with name:ImageTwo"));
        assertTrue(result.contains("Table with Title: Table 1"));

        assertTrue(result.contains("Capitolul 1.1"));
        assertTrue(result.contains("Capitolul 1.1.1"));
    }
}
