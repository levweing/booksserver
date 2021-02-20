package books.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Book {
    @Id
    private String isbn;
    
    private String name;
    private int quantity;
    private double price;
    
    @Version
    private int version;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Author> authors;
    
    public Book() {
    }
    
    public Book(String isbn, String name, Author ...authors) {
        this.isbn = isbn;
        this.name = name;
        this.authors = new HashSet<Author>(Arrays.asList(authors));
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public String getName() {
        return name;
    }
    
    public Book setName(String value) {
        name = value;
        return this;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public Book setQuantity(int value) {
        quantity = value;
        return this;
    }
    
    public double getPrice() {
        return price;
    }
    
    public Book setPrice(double value) {
        price = value;
        return this;
    }
    
    public int getVersion() {
        return version;
    }
    
    public void setVersion(int value) {
        version = value;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Book setAuthors(Set<Author> value) {
        authors = value;
        return this;
    }

    @Override
    public String toString() {
        final String result = String.format(
            "Book[isbn=%s, name=%s, quantity=%d, authors=%s]",
            isbn, name, quantity, authors);
        
        return result;
    }
}
