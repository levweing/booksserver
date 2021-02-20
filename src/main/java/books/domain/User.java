package books.domain;

import java.util.List;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String role;
    
    @OneToMany
    private List<Order> orders;
    
    public User() {
    }
    
    public User(
        final String username, final String password, final String role) {
        
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(final Long value) {
        id = value;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(final String value) {
        username = value;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(final String value) {
        password = value;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(final String value) {
        role = value;
    }
}
