package books.domain;

import javax.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne
    private Book book;
    
    private double price;
    private int quantity;

    public OrderItem() {
    }
    
    public OrderItem(Book book, double price, int quantity) {
        this.book = book;
        this.price = price;
        this.quantity = quantity;
    }
    
    public long getId() {
        return id;
    }
    
    public Book getBook() {
        return book;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return quantity;
    }
}
