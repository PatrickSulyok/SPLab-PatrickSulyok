
package ro.uvt.sp.lab1.commands;

import ro.uvt.sp.lab1.services.BooksService;

public class CreateBookCommand implements Command<Long> {

    private final BooksService booksService;
    private final String title;

    public CreateBookCommand(BooksService booksService, String title) {
        this.booksService = booksService;
        this.title = title;
    }

    @Override
    public Long execute() {
        return booksService.createBook(title);
    }
}

