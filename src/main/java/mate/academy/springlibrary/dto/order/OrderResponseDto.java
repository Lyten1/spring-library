package mate.academy.springlibrary.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import mate.academy.springlibrary.model.OrderStatus;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private Set<OrderItemResponseDto> orderItems;
}

