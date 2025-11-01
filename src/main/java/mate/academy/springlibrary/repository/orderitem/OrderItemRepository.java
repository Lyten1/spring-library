package mate.academy.springlibrary.repository.orderitem;

import java.util.List;
import mate.academy.springlibrary.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    OrderItem findByIdAndOrderId(Long itemId, Long orderId);
}
