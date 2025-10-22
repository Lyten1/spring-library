package mate.academy.springlibrary.repository.cartitem;

import mate.academy.springlibrary.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("select item from CartItem item "
            + "where item.id = :itemId AND item.shoppingCart.id = :cartId")
    CartItem findByIdAndShoppingCartId(@Param("itemId") Long cartItemId,
                                       @Param("cartId") Long shopCartId);
}
