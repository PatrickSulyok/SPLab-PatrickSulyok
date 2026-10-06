package ro.uvt.sp.lab1;

public class AlignRight implements AlignStrategy {

    @Override
    public void render(Paragraph paragraph, Context context) {
        System.out.println("Paragraph: " + paragraph.getText() + " (Aligned Right)");
    }
}