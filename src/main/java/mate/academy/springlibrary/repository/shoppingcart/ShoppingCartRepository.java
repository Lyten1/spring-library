package mate.academy.springlibrary.repository.shoppingcart;

import mate.academy.springlibrary.model.ShoppingCart;
import mate.academy.springlibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUser(User user);
}
