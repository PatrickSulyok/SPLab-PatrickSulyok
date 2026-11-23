
package ro.uvt.sp.lab1.persistence;

import org.springframework.stereotype.Repository;
import ro.uvt.sp.lab1.Book;

import java.util.List;
import java.util.Optional;

@Repository
public class BooksRepositoryAdapter implements CrudRepository<Book, Long> {

    private final BooksRepository booksRepository;

    public BooksRepositoryAdapter(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public Book save(Book entity) {
        return booksRepository.save(entity);
    }

    @Override
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return booksRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return booksRepository.existsById(id);
    }

    @Override
    public void deleteById(Long id) {
        booksRepository.deleteById(id);
    }
}
