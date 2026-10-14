package ro.uvt.sp.lab1;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpLabPatrickSulyokApplication {

    public static void main(String[] args) {
        Section cap1 = new Section("Capitolul 1");

        Paragraph p1 = new Paragraph("Paragraph 1");
        cap1.add(p1);

        Paragraph p2 = new Paragraph("Paragraph 2");
        cap1.add(p2);

        Paragraph p3 = new Paragraph("Paragraph 3");
        cap1.add(p3);

        Paragraph p4 = new Paragraph("Paragraph 4");
        cap1.add(p4);

        cap1.add(new ImageProxy("ImageOne"));
        cap1.add(new Image("ImageTwo"));
        cap1.add(new Paragraph("Some text"));
        cap1.add(new Table("Table 1"));

        BookStatistics stats = new BookStatistics();
        cap1.accept(stats);
        stats.printStatistics();

        System.out.println();
        System.out.println("Rendering content:");
        cap1.accept(new RenderContentVisitor());

        Section cap11 = new Section("Capitolul 1.1");
        cap1.add(cap11);

        cap11.add(new Paragraph("Text din subcapitolul 1.1"));

        Section cap111 = new Section("Capitolul 1.1.1");
        cap11.add(cap111);

        cap111.add(new Paragraph("Text din subcapitolul 1.1.1"));

        GenerateToC toc = new GenerateToC();
        cap1.accept(toc);

        System.out.println();
        System.out.println(toc.getToC());
    }
}