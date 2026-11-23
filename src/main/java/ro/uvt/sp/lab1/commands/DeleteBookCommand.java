package ro.uvt.sp.lab1.commands;

public class DeleteBookCommand implements Command<Boolean> {

    private final CommandContext context;
    private final Long id;

    public DeleteBookCommand(CommandContext context, Long id) {
        this.context = context;
        this.id = id;
    }

    @Override
    public Boolean execute() {
        if (!context.getBooksRepository().existsById(id)) {
            return false;
        }

        context.getBooksRepository().deleteById(id);
        return true;
    }
}