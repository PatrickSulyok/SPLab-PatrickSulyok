
package ro.uvt.sp.lab1.commands;

import ro.uvt.sp.lab1.services.BooksService;

public class GetBookByIdCommand implements Command<String> {

    private final BooksService booksService;
    private final Long id;

    public GetBookByIdCommand(BooksService booksService, Long id) {
        this.booksService = booksService;
        this.id = id;
    }

    @Override
    public String execute() {
        return booksService.getBookById(id);
    }
}
