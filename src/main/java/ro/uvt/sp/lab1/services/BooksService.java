
package ro.uvt.sp.lab1.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.uvt.sp.lab1.Book;
import ro.uvt.sp.lab1.persistence.CrudRepository;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
public class BooksService {

    private final CrudRepository<Book, Long> booksRepository;

    public BooksService(CrudRepository<Book, Long> booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Transactional(readOnly = true)
    public Map<Long, String> getAllBooks() {
        Map<Long, String> books = new LinkedHashMap<>();

        for (Book book : booksRepository.findAll()) {
            books.put(book.getId(), book.getTitle());
        }

        return books;
    }

    @Transactional(readOnly = true)
    public String getBookById(Long id) {
        return booksRepository.findById(id)
                .map(Book::getTitle)
                .orElse(null);
    }

    public Long createBook(String title) {
        Book book = new Book(title);
        return booksRepository.save(book).getId();
    }

    public boolean updateBook(Long id, String title) {
        return booksRepository.findById(id)
                .map(book -> {
                    book.setTitle(title);
                    booksRepository.save(book);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteBook(Long id) {
        if (!booksRepository.existsById(id)) {
            return false;
        }

        booksRepository.deleteById(id);
        return true;
    }
}
