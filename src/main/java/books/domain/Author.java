package books.domain;

import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Author {
    @Id
    @GeneratedValue
    private long authorId;
    
    private String firstName;
    private String lastName;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private Set<Book> books;

    public Author() {
    }
    
    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public long getId() {
        return authorId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(final String value) {
        firstName = value;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(final String value) {
        lastName = value;
    }

    public Set<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        final String result = String.format(
            "Author[authorId=%d, firstName=%s, lastName=%s]",
            authorId, firstName, lastName);
        
        return result;
    }
}
