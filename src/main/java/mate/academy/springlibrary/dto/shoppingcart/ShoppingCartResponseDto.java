package mate.academy.springlibrary.dto.shoppingcart;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.CartItemResponseDto;

@Getter
@Setter
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private List<CartItemResponseDto> cartItems;
}
