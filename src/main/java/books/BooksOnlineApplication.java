package books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import books.domain.Author;
import books.domain.Book;
import books.domain.User;
import books.repository.AuthorRepository;
import books.repository.BookRepository;
import books.repository.UserRepository;

@SpringBootApplication
public class BooksOnlineApplication {
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private void insertBooks(Book... books) {
        for (Book b : books) {
            authorRepository.saveAll(b.getAuthors());
            bookRepository.save(b);
        }
    }
    
    private void insertUsers() {
        // user/user
        userRepository.save(new User("user",
            "$2y$12$GDGOcQgqWdhrRjTZZ1Im3eXn2Yw1trobnojCrClfLInwyTZmmFmYq",
            "USER"));
        
        // admin/admin
        userRepository.save(new User("admin",
            "$2y$12$RGSLA/QjBlbmsPe01EtcAe5kCRhwhdZfK2ZH14KSfBvSBZUNCIAwK",
            "ADMIN"));
    }
    
    private void prepareData(String[] args) {
        Book raj = new Book(
            "111", "Romeo and Juliet", new Author("William", "Shakespeare"));
        
        Book wap = new Book(
            "222", "War and Peace", new Author("Leo", "Tolstoy"));
        
        Book cap = new Book(
            "333", "Crime and Punishment", new Author("Fyodor", "Dostoevsky"));
        
        insertBooks(raj.setPrice(15).setQuantity(10),
            wap.setPrice(25).setQuantity(7),
            cap.setPrice(50.5).setQuantity(1));
        
        insertUsers();
        
        bookRepository.findAll().forEach(System.out::println);
        authorRepository.findAll().forEach(System.out::println);
    }
    
    @Bean
    CommandLineRunner runner() {
        final CommandLineRunner result = this::prepareData;
        
        return result;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(BooksOnlineApplication.class, args);
    }
}
