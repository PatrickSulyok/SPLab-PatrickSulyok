
package ro.uvt.sp.lab1;

import org.junit.jupiter.api.Test;
import ro.uvt.sp.lab1.commands.*;
import ro.uvt.sp.lab1.services.BooksService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BooksServiceTests {

    @Test
    void createAndRetrieveBook() {
        BooksService service = new BooksService();

        Long id = new CreateBookCommand(service, "Book One").execute();

        assertEquals("Book One",
                new GetBookByIdCommand(service, id).execute());
    }

    @Test
    void retrieveAllBooks() {
        BooksService service = new BooksService();

        new CreateBookCommand(service, "First").execute();
        new CreateBookCommand(service, "Second").execute();

        Map<Long, String> books =
                new GetAllBooksCommand(service).execute();

        assertEquals(2, books.size());
        assertTrue(books.containsValue("First"));
        assertTrue(books.containsValue("Second"));
    }

    @Test
    void updateExistingBook() {
        BooksService service = new BooksService();

        Long id = new CreateBookCommand(service, "Old Title").execute();

        assertTrue(new UpdateBookCommand(service, id, "New Title").execute());
        assertEquals("New Title",
                new GetBookByIdCommand(service, id).execute());
    }

    @Test
    void deleteExistingBook() {
        BooksService service = new BooksService();

        Long id = new CreateBookCommand(service, "Temporary").execute();

        assertTrue(new DeleteBookCommand(service, id).execute());
        assertNull(new GetBookByIdCommand(service, id).execute());
    }

    @Test
    void missingBookOperationsFail() {
        BooksService service = new BooksService();

        assertNull(new GetBookByIdCommand(service, 999L).execute());
        assertFalse(new UpdateBookCommand(service, 999L, "Title").execute());
        assertFalse(new DeleteBookCommand(service, 999L).execute());
    }

    @Test
    void synchronousExecutorReturnsResult() {
        BooksService service = new BooksService();
        CommandExecutor executor = new SynchronousCommandExecutor();

        Long id = executor.execute(
                new CreateBookCommand(service, "Sync Book")
        ).join();

        assertEquals("Sync Book", service.getBookById(id));
    }

    @Test
    void asynchronousExecutorReturnsResult() {
        BooksService service = new BooksService();

        try (AsynchronousCommandExecutor executor =
                     new AsynchronousCommandExecutor()) {

            Long id = executor.execute(
                    new CreateBookCommand(service, "Async Book")
            ).join();

            assertEquals("Async Book", service.getBookById(id));
        }
    }
}
