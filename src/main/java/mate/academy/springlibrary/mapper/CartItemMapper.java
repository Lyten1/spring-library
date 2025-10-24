package mate.academy.springlibrary.mapper;

import mate.academy.springlibrary.config.MapperConfig;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.AddToCartRequestDto;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.CartItemResponseDto;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.UpdateCartItemRequestDto;
import mate.academy.springlibrary.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {

    CartItem toModel(AddToCartRequestDto requestDto);

    @Mapping(source = "book.id", target = "bookId")
    @Mapping(source = "book.title", target = "bookTitle")
    CartItemResponseDto toDto(CartItem model);

    void updateItemFromDto(UpdateCartItemRequestDto requestDto, @MappingTarget CartItem cartItem);
}
