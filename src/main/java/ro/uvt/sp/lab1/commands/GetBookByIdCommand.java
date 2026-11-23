package ro.uvt.sp.lab1.commands;

import ro.uvt.sp.lab1.Book;

public class GetBookByIdCommand implements Command<Book> {

    private final CommandContext context;
    private final Long id;

    public GetBookByIdCommand(CommandContext context, Long id) {
        this.context = context;
        this.id = id;
    }

    @Override
    public Book execute() {
        return context.getBooksRepository()
                .findById(id)
                .orElse(null);
    }
}