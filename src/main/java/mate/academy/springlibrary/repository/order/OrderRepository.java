package mate.academy.springlibrary.repository.order;

import mate.academy.springlibrary.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
