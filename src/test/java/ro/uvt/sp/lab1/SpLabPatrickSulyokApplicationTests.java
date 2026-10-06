package ro.uvt.sp.lab1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SpLabPatrickSulyokApplicationTests {

    @Test
    void mainExecutesProxyExample() {
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

        assertTrue(result.contains("Creation of the content took"));
        assertTrue(result.contains("Front Cover"));
        assertTrue(result.contains("Image with name:Pamela Anderson"));
        assertTrue(result.contains("Printing of the section 1 took"));
        assertTrue(result.contains("Printing again the section 1 took"));
    }
}