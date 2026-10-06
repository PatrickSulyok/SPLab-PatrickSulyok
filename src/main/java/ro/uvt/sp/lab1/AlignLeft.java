package ro.uvt.sp.lab1;

public class AlignLeft implements AlignStrategy {


    @Override
    public void render(Paragraph paragraph, Context context) {
        System.out.println("Paragraph: " + paragraph.getText() + " (Aligned Left)");
    }
}