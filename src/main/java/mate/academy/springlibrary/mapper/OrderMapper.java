package mate.academy.springlibrary.mapper;

import mate.academy.springlibrary.config.MapperConfig;
import mate.academy.springlibrary.dto.order.OrderResponseDto;
import mate.academy.springlibrary.dto.order.OrderUpdateStatusRequestDto;
import mate.academy.springlibrary.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    OrderResponseDto toDto(Order order);

    void updateOrderFromDto(OrderUpdateStatusRequestDto dto, @MappingTarget Order order);
}
