package ro.uvt.sp.lab1;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpLabPatrickSulyokApplicationTests {

    @Test
    void mainPrintsTheExactProfessorExampleWithoutStartingSpring() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream capture = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            SpLabPatrickSulyokApplication.main(new String[0]);
        } finally {
            System.setOut(original);
        }

        String expected = """
                Book: Noapte buna, copii!

                Authors:
                Author: Radu Pavel Gheo

                Paragraph: Multumesc celor care ...
                Capitolul 1
                Paragraph: Moto capitol
                Capitolul 1.1
                Paragraph: Text from subchapter 1.1
                Capitolul 1.1.1
                Paragraph: Text from subchapter 1.1.1
                Subchapter 1.1.1.1
                Image with name:Image subchapter 1.1.1.1
                """;
        assertEquals(expected, output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n"));
    }

}
