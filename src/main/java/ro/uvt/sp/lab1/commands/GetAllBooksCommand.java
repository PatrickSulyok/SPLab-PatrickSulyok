
package ro.uvt.sp.lab1.commands;

import ro.uvt.sp.lab1.services.BooksService;

import java.util.Map;

public class GetAllBooksCommand implements Command<Map<Long, String>> {

    private final BooksService booksService;

    public GetAllBooksCommand(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public Map<Long, String> execute() {
        return booksService.getAllBooks();
    }
}
