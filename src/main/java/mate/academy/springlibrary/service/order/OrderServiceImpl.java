package mate.academy.springlibrary.service.order;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.order.OrderItemResponseDto;
import mate.academy.springlibrary.dto.order.OrderRequestDto;
import mate.academy.springlibrary.dto.order.OrderResponseDto;
import mate.academy.springlibrary.dto.order.OrderUpdateStatusRequestDto;
import mate.academy.springlibrary.exeption.EntityNotFoundException;
import mate.academy.springlibrary.exeption.OrderProcessingException;
import mate.academy.springlibrary.mapper.OrderItemMapper;
import mate.academy.springlibrary.mapper.OrderMapper;
import mate.academy.springlibrary.model.CartItem;
import mate.academy.springlibrary.model.Order;
import mate.academy.springlibrary.model.OrderItem;
import mate.academy.springlibrary.model.OrderStatus;
import mate.academy.springlibrary.model.ShoppingCart;
import mate.academy.springlibrary.repository.order.OrderRepository;
import mate.academy.springlibrary.repository.orderitem.OrderItemRepository;
import mate.academy.springlibrary.service.shoppingcart.ShoppingCartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ShoppingCartService shoppingCartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderResponseDto save(OrderRequestDto requestDto) {
        ShoppingCart currentShoppingCartEntity = shoppingCartService.getCurrentShoppingCartEntity();
        if (currentShoppingCartEntity.getCartItems().isEmpty()) {
            throw new OrderProcessingException("Can't process order without items");
        }
        Order order = new Order();
        order.setShippingAddress(requestDto.getShippingAddress());
        order.setUser(currentShoppingCartEntity.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setOrderItems(currentShoppingCartEntity.getCartItems().stream()
                .map(item -> transferItemFromCartToOrder(item, order))
                .collect(Collectors.toSet()));
        order.setTotal(order.getOrderItems().stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        Order savedOrder = orderRepository.save(order);
        shoppingCartService.clearShoppingCart(currentShoppingCartEntity);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public Page<OrderResponseDto> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public OrderResponseDto update(Long id, OrderUpdateStatusRequestDto requestDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find order with id:" + id));
        orderMapper.updateOrderFromDto(requestDto, order);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemResponseDto> getAllOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId).stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId) {
        return orderItemMapper.toDto(orderItemRepository.findByIdAndOrderId(itemId, orderId));
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
