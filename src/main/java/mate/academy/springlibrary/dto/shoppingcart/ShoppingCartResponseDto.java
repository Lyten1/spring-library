package mate.academy.springlibrary.dto.shoppingcart;

import mate.academy.springlibrary.dto.shoppingcart.cartitem.CartItemResponseDto;

import java.util.List;

public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> cartItems;
}
