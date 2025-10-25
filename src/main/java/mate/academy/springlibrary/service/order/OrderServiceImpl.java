package mate.academy.springlibrary.service.order;

import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.order.OrderRequestDto;
import mate.academy.springlibrary.dto.order.OrderResponseDto;
import mate.academy.springlibrary.model.*;
import mate.academy.springlibrary.repository.order.OrderRepository;
import mate.academy.springlibrary.service.shoppingcart.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ShoppingCartService shoppingCartService;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponseDto save(OrderRequestDto requestDto) {
        ShoppingCart currentShoppingCartEntity = shoppingCartService.getCurrentShoppingCartEntity();
        Order order = new Order();
        order.setUser(currentShoppingCartEntity.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderItems(currentShoppingCartEntity.getCartItems().stream()
                .map(item -> transferItemFromCartToOrder(item, order))
                .collect(Collectors.toSet()));

        Order savedOrder = orderRepository.save(order);
        //TODO shoppingCartService.clear(currentShoppingCartEntity);

        return null;
    }

    @Override
    public List<OrderResponseDto> getAll() {
        return List.of();
    }

    private OrderItem transferItemFromCartToOrder(CartItem item, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(item.getBook());
        orderItem.setQuantity(item.getQuantity());
        orderItem.setPrice(item.getBook().getPrice());
        orderItem.setOrder(order);
        return orderItem;
    }

}
