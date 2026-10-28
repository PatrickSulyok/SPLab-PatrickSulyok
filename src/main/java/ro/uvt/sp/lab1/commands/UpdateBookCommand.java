
package ro.uvt.sp.lab1.commands;

import ro.uvt.sp.lab1.services.BooksService;

public class UpdateBookCommand implements Command<Boolean> {

    private final BooksService booksService;
    private final Long id;
    private final String title;

    public UpdateBookCommand(BooksService booksService, Long id, String title) {
        this.booksService = booksService;
        this.id = id;
        this.title = title;
    }

    @Override
    public Boolean execute() {
        return booksService.updateBook(id, title);
    }
}
