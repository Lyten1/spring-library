package mate.academy.springlibrary.service.order;

import java.util.List;
import mate.academy.springlibrary.dto.order.OrderItemResponseDto;
import mate.academy.springlibrary.dto.order.OrderRequestDto;
import mate.academy.springlibrary.dto.order.OrderResponseDto;
import mate.academy.springlibrary.dto.order.OrderUpdateStatusRequestDto;

public interface OrderService {

    OrderResponseDto save(OrderRequestDto requestDto);

    List<OrderResponseDto> getAll();

    OrderResponseDto update(Long id, OrderUpdateStatusRequestDto requestDto);

    List<OrderItemResponseDto> getAllOrderItems(Long orderId);

    OrderItemResponseDto getOrderItem(Long orderId, Long itemId);
}
