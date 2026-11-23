package ro.uvt.sp.lab1.commands;

import ro.uvt.sp.lab1.Book;

public class CreateBookCommand implements Command<Long> {

    private final CommandContext context;
    private final String title;

    public CreateBookCommand(CommandContext context, String title) {
        this.context = context;
        this.title = title;
    }

    @Override
    public Long execute() {
        Book book = new Book(title);
        return context.getBooksRepository().save(book).getId();
    }
}