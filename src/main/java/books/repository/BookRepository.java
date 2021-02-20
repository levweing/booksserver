package books.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import books.domain.Book;

@RepositoryRestResource
public interface BookRepository extends CrudRepository<Book, String> {
    @Query("select b from Book b where b.quantity > 0")
    List<Book> findAvailableBooks();
}
