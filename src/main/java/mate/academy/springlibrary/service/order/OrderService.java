package mate.academy.springlibrary.service.order;

import mate.academy.springlibrary.dto.order.OrderRequestDto;
import mate.academy.springlibrary.dto.order.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto save(OrderRequestDto requestDto);

    List<OrderResponseDto> getAll();
}
