package mate.academy.springlibrary.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private ShoppingCart shoppingCart;
    @OneToOne
    @JoinColumn(nullable = false)
    private Book book;
    @Column(nullable = false)
    private int quantity;
}
