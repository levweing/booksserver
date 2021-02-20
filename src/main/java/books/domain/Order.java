package books.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "book_order")
public class Order {
    @Id
    @GeneratedValue
    private long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @ManyToOne
    private User user;
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;
    
    public Order() {
    }
    
    public Order(User user, Date creationDate, List<OrderItem> orderItems) {
        this.user = user;
        this.creationDate = creationDate;
        this.orderItems = orderItems;
    }
    
    public long getId() {
        return id;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(Date value) {
        creationDate = value;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User value) {
        user = value;
    }
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> value) {
        orderItems = value;
    }
}
