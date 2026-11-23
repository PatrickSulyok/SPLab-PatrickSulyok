package ro.uvt.sp.lab1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ro.uvt.sp.lab1.commands.*;
import ro.uvt.sp.lab1.persistence.CrudRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:booktests;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class BooksServiceTests {

    @Autowired
    private CrudRepository<Book, Long> repository;

    private CommandContext context;

    @BeforeEach
    void setUp() {
        for (Book book : repository.findAll()) {
            repository.deleteById(book.getId());
        }

        context = new CommandContext(repository);
    }

    @Test
    void createAndRetrieveBook() {
        Long id = new CreateBookCommand(context, "Book One").execute();

        Book book = new GetBookByIdCommand(context, id).execute();

        assertNotNull(book);
        assertEquals("Book One", book.getTitle());
    }

    @Test
    void retrieveAllBooks() {
        new CreateBookCommand(context, "First").execute();
        new CreateBookCommand(context, "Second").execute();

        List<Book> books = new GetAllBooksCommand(context).execute();

        assertEquals(2, books.size());
        assertTrue(books.stream()
                .anyMatch(book -> book.getTitle().equals("First")));
        assertTrue(books.stream()
                .anyMatch(book -> book.getTitle().equals("Second")));
    }

    @Test
    void updateExistingBook() {
        Long id = new CreateBookCommand(context, "Old Title").execute();

        assertTrue(new UpdateBookCommand(context, id, "New Title").execute());

        Book book = new GetBookByIdCommand(context, id).execute();

        assertEquals("New Title", book.getTitle());
    }

    @Test
    void deleteExistingBook() {
        Long id = new CreateBookCommand(context, "Temporary").execute();

        assertTrue(new DeleteBookCommand(context, id).execute());
        assertNull(new GetBookByIdCommand(context, id).execute());
    }

    @Test
    void missingBookOperationsFail() {
        assertNull(new GetBookByIdCommand(context, 999L).execute());
        assertFalse(new UpdateBookCommand(context, 999L, "Title").execute());
        assertFalse(new DeleteBookCommand(context, 999L).execute());
    }

    @Test
    void synchronousExecutorReturnsResult() {
        CommandExecutor executor = new SynchronousCommandExecutor();

        Long id = executor.execute(
                new CreateBookCommand(context, "Sync Book")
        ).join();

        assertEquals("Sync Book",
                new GetBookByIdCommand(context, id).execute().getTitle());
    }

    @Test
    void asynchronousExecutorReturnsResult() {
        try (AsynchronousCommandExecutor executor =
                     new AsynchronousCommandExecutor()) {

            Long id = executor.execute(
                    new CreateBookCommand(context, "Async Book")
            ).join();

            assertEquals("Async Book",
                    new GetBookByIdCommand(context, id).execute().getTitle());
        }
    }
}