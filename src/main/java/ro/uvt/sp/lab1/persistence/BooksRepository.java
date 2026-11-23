
package ro.uvt.sp.lab1.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.uvt.sp.lab1.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
}
