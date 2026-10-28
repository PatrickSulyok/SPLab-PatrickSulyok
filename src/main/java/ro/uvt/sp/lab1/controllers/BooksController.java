
package ro.uvt.sp.lab1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.uvt.sp.lab1.commands.*;
import ro.uvt.sp.lab1.services.AsyncCommandService;
import ro.uvt.sp.lab1.services.BooksService;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final AsyncCommandService asyncCommandService;

    public BooksController(
            BooksService booksService,
            AsyncCommandService asyncCommandService
    ) {
        this.booksService = booksService;
        this.asyncCommandService = asyncCommandService;
    }

    @GetMapping
    public ResponseEntity<Map<Long, String>> getAllBooks() {
        GetAllBooksCommand command = new GetAllBooksCommand(booksService);
        return ResponseEntity.ok(command.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getBookById(@PathVariable Long id) {
        GetBookByIdCommand command = new GetBookByIdCommand(booksService, id);
        String title = command.execute();

        if (title == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(title);
    }

    @PostMapping
    public ResponseEntity<Long> createBook(@RequestBody String title) {
        CreateBookCommand command = new CreateBookCommand(booksService, title);
        Long id = command.execute();

        return ResponseEntity.created(URI.create("/books/" + id)).body(id);
    }

    @PostMapping("/async")
    public ResponseEntity<Map<String, String>> createBookAsync(
            @RequestBody String title
    ) {
        CreateBookCommand command = new CreateBookCommand(booksService, title);
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
        UpdateBookCommand command = new UpdateBookCommand(booksService, id, title);

        if (!command.execute()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        DeleteBookCommand command = new DeleteBookCommand(booksService, id);

        if (!command.execute()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
