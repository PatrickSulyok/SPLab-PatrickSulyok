
package ro.uvt.sp.lab1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uvt.sp.lab1.Book;
import ro.uvt.sp.lab1.commands.*;
import ro.uvt.sp.lab1.persistence.CrudRepository;
import ro.uvt.sp.lab1.services.AsyncCommandService;
import ro.uvt.sp.lab1.observer.AllBooksSubject;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final CommandContext context;
    private final AsyncCommandService asyncCommandService;
    private final AllBooksSubject allBooksSubject;

    public BooksController(
            CrudRepository<Book, Long> booksRepository,
            AsyncCommandService asyncCommandService,
            AllBooksSubject allBooksSubject) {
        this.context = new CommandContext(booksRepository);
        this.asyncCommandService = asyncCommandService;
        this.allBooksSubject = allBooksSubject;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(
                new GetAllBooksCommand(context).execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = new GetBookByIdCommand(context, id).execute();

        return book == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Long> createBook(@RequestBody String title) {
        Long id = new CreateBookCommand(context, title).execute();

        context.getBooksRepository()
                .findById(id)
                .ifPresent(allBooksSubject::add);

        return ResponseEntity.created(
                URI.create("/books/" + id)).body(id);
    }

    @PostMapping("/async")
    public ResponseEntity<Map<String, String>> createBookAsync(
            @RequestBody String title) {

        Command<Long> command = () -> {
            Long id = new CreateBookCommand(context, title).execute();

            context.getBooksRepository()
                    .findById(id)
                    .ifPresent(allBooksSubject::add);

            return id;
        };

        String taskId = asyncCommandService.submit(command);

        return ResponseEntity.accepted().body(
                Map.of("taskId", taskId, "status", "ACCEPTED"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBook(
            @PathVariable Long id,
            @RequestBody String title) {

        boolean updated =
                new UpdateBookCommand(context, id, title).execute();

        return updated
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted =
                new DeleteBookCommand(context, id).execute();

        return deleted
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
