package ro.uvt.sp.lab1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uvt.sp.lab1.Book;
import ro.uvt.sp.lab1.commands.*;
import ro.uvt.sp.lab1.persistence.CrudRepository;
import ro.uvt.sp.lab1.services.AsyncCommandService;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final CommandContext context;
    private final AsyncCommandService asyncCommandService;

    public BooksController(
            CrudRepository<Book, Long> booksRepository,
            AsyncCommandService asyncCommandService
    ) {
        this.context = new CommandContext(booksRepository);
        this.asyncCommandService = asyncCommandService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        GetAllBooksCommand command = new GetAllBooksCommand(context);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        GetBookByIdCommand command = new GetBookByIdCommand(context, id);
        Book book = command.execute();

        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Long> createBook(@RequestBody String title) {
        CreateBookCommand command = new CreateBookCommand(context, title);
        Long id = command.execute();

        return ResponseEntity.created(URI.create("/books/" + id)).body(id);
    }

    @PostMapping("/async")
    public ResponseEntity<Map<String, String>> createBookAsync(
            @RequestBody String title
    ) {
        CreateBookCommand command = new CreateBookCommand(context, title);
        String taskId = asyncCommandService.submit(command);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(Map.of(
                        "taskId", taskId,
                        "status", "ACCEPTED"
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(
            @PathVariable Long id,
            @RequestBody String title
    ) {
        UpdateBookCommand command =
                new UpdateBookCommand(context, id, title);

        if (!command.execute()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        DeleteBookCommand command = new DeleteBookCommand(context, id);

        if (!command.execute()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}