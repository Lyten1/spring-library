package mate.academy.springlibrary.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
