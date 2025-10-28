package mate.academy.springlibrary.repository.orderitem;

import mate.academy.springlibrary.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);


    OrderItem findByIdAndOrderId(Long itemId, Long orderId);
}
