package ro.uvt.sp.lab1.commands;

import ro.uvt.sp.lab1.Book;
import ro.uvt.sp.lab1.persistence.CrudRepository;

public class CommandContext {

    private final CrudRepository<Book, Long> booksRepository;

    public CommandContext(CrudRepository<Book, Long> booksRepository) {
        this.booksRepository = booksRepository;
    }

    public CrudRepository<Book, Long> getBooksRepository() {
        return booksRepository;
    }
}
