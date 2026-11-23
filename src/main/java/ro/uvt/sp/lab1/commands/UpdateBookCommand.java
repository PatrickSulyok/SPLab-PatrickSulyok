package ro.uvt.sp.lab1.commands;

public class UpdateBookCommand implements Command<Boolean> {

    private final CommandContext context;
    private final Long id;
    private final String title;

    public UpdateBookCommand(CommandContext context, Long id, String title) {
        this.context = context;
        this.id = id;
        this.title = title;
    }

    @Override
    public Boolean execute() {
        return context.getBooksRepository().findById(id)
                .map(book -> {
                    book.setTitle(title);
                    context.getBooksRepository().save(book);
                    return true;
                })
                .orElse(false);
    }
}