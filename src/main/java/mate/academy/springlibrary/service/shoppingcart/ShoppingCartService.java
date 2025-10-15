package mate.academy.springlibrary.service.shoppingcart;

import mate.academy.springlibrary.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springlibrary.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCartResponseDto getCurrentUsersShoppingCart();
}
