package ro.uvt.sp.lab1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpLabPatrickSulyokApplicationTests {

    @Test
    void mainPrintsStrategyExampleWithoutStartingSpring() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;

        try (PrintStream capture =
                     new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            SpLabPatrickSulyokApplication.main(new String[0]);
        } finally {
            System.setOut(original);
        }

        String expected = """
                Printing without Alignment

                Capitolul 1
                Paragraph: Paragraph 1
                Paragraph: Paragraph 2
                Paragraph: Paragraph 3
                Paragraph: Paragraph 4

                Printing with Alignment

                Capitolul 1
                Paragraph: Paragraph 1 (Aligned Center)
                Paragraph: Paragraph 2 (Aligned Right)
                Paragraph: Paragraph 3 (Aligned Left)
                Paragraph: Paragraph 4
                """;

        assertEquals(
                expected,
                output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n")
        );
    }
}
