package books.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import books.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
