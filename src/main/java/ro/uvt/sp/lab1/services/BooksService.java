
package ro.uvt.sp.lab1.services;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BooksService {

    private final Map<Long, String> books = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public Map<Long, String> getAllBooks() {
        return new LinkedHashMap<>(books);
    }

    public String getBookById(Long id) {
        return books.get(id);
    }

    public Long createBook(String title) {
        Long id = nextId.getAndIncrement();
        books.put(id, title);
        return id;
    }

    public boolean updateBook(Long id, String title) {
        return books.replace(id, title) != null;
    }

    public boolean deleteBook(Long id) {
        return books.remove(id) != null;
    }
}
