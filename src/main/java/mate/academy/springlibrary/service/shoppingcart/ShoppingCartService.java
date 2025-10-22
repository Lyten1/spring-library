package mate.academy.springlibrary.service.shoppingcart;

import mate.academy.springlibrary.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.AddToCartRequestDto;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.UpdateCartItemRequestDto;
import mate.academy.springlibrary.model.ShoppingCart;
import mate.academy.springlibrary.model.User;

public interface ShoppingCartService {

    ShoppingCart create(User user);

    ShoppingCartResponseDto getCurrentUsersShoppingCart();

    ShoppingCartResponseDto addBookToShoppingCart(AddToCartRequestDto requestDto);

    ShoppingCartResponseDto updateShoppingCartItem(Long cartItemId,
                                                   UpdateCartItemRequestDto cartItemDto);

    void deleteShoppingCartItem(Long cartItemId);
}
