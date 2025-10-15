package mate.academy.springlibrary.mapper;

import mate.academy.springlibrary.config.MapperConfig;
import mate.academy.springlibrary.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springlibrary.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

}
