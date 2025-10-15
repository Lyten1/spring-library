package mate.academy.springlibrary.service.shoppingcart;

import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springlibrary.mapper.ShoppingCartMapper;
import mate.academy.springlibrary.model.ShoppingCart;
import mate.academy.springlibrary.model.User;
import mate.academy.springlibrary.repository.shoppingcart.ShoppingCartRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getCurrentUsersShoppingCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return shoppingCartMapper.toDto(shoppingCartRepository.findByUser(user));
    }
}
