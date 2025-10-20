package mate.academy.springlibrary.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.Set;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE shopping_carts SET is_deleted = true WHERE id=?")
@SQLRestriction(value = "is_deleted=false")
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "shoppingCart",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private Set<CartItem> cartItems;

    @Column(nullable = false)
    private boolean isDeleted = false;
}
