package books.web;

import java.util.*;
import java.util.stream.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import books.domain.Book;
import books.domain.Order;
import books.domain.OrderItem;
import books.repository.BookRepository;
import books.repository.UserRepository;

@RestController
public class BooksController {
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @RequestMapping("api/books")
    @Transactional(readOnly = true)
    public Iterable<Book> getBooks() {
        Iterable<Book> result = bookRepository.findAvailableBooks();
        
        return result;
    }
    
    @RequestMapping(value = "api/orderBooks", method = RequestMethod.POST)
    @Transactional
    public void orderBooks(@RequestBody Map<String, Integer> quantitiesMap) {
        Iterable<Book> books =
            bookRepository.findAllById(quantitiesMap.keySet());
        
        books.forEach(b ->
            entityManager.merge(b.setQuantity(b.getQuantity() - 1)));
        
        Stream<OrderItem> itemsStream = StreamSupport.stream(
            books.spliterator(), false).map(b -> new OrderItem(b, b.getPrice(),
            quantitiesMap.get(b.getIsbn())));
        
        List<OrderItem> orderItems = itemsStream.collect(Collectors.toList());
 
        orderItems.forEach(entityManager::persist);
        
        Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

        Order order = new Order(userRepository.findByUsername(
            authentication.getName()), new Date(), orderItems);
        
        entityManager.persist(order);
    }
}
