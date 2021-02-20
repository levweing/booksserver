package books.repository;

import org.springframework.data.repository.CrudRepository;

import books.domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
