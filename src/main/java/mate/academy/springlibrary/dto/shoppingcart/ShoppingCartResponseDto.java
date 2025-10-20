package mate.academy.springlibrary.dto.shoppingcart;

import java.util.List;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.CartItemResponseDto;

public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> cartItems;
}
