package mate.academy.springlibrary.dto.shoppingcart.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddToCartRequestDto {
    @NotNull
    private Long bookId;

    @Min(1)
    private int quantity;
}
