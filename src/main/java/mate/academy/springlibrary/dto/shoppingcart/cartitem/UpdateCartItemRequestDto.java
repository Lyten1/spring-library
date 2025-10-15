package mate.academy.springlibrary.dto.shoppingcart.cartitem;

import jakarta.validation.constraints.Min;

public class UpdateCartItemRequestDto {
    @Min(1)
    private int quantity;
}
