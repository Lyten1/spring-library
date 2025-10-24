package mate.academy.springlibrary.service.shoppingcart;

import jakarta.transaction.Transactional;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.AddToCartRequestDto;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.UpdateCartItemRequestDto;
import mate.academy.springlibrary.exeption.EntityNotFoundException;
import mate.academy.springlibrary.mapper.CartItemMapper;
import mate.academy.springlibrary.mapper.ShoppingCartMapper;
import mate.academy.springlibrary.model.CartItem;
import mate.academy.springlibrary.model.ShoppingCart;
import mate.academy.springlibrary.model.User;
import mate.academy.springlibrary.repository.cartitem.CartItemRepository;
import mate.academy.springlibrary.repository.shoppingcart.ShoppingCartRepository;
import mate.academy.springlibrary.repository.users.UserRepository;
import mate.academy.springlibrary.service.book.BookService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookService bookService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;
    private final UserRepository userRepository;

    @Override
    public void create(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto getCurrentUsersShoppingCart() {
        ShoppingCart currentShoppingCart = getCurrentShoppingCart();
        return shoppingCartMapper.toDto(currentShoppingCart);
    }

    @Override
    public ShoppingCartResponseDto addBookToShoppingCart(AddToCartRequestDto requestDto) {
        ShoppingCart shoppingCart = getCurrentShoppingCart();
        CartItem newItem = cartItemMapper.toModel(requestDto);
        newItem.setBook(bookService.getEntityById(requestDto.getBookId()));
        addOrUpdateCartItem(shoppingCart, newItem);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCartResponseDto updateShoppingCartItem(Long cartItemId,
                                                          UpdateCartItemRequestDto cartItemDto) {
        ShoppingCart shoppingCart = getCurrentShoppingCart();
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(
                cartItemId, shoppingCart.getId());
        cartItemMapper.updateItemFromDto(cartItemDto, cartItem);
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public void deleteShoppingCartItem(Long cartItemId) {
        ShoppingCart shoppingCart = getCurrentShoppingCart();
        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(
                cartItemId, shoppingCart.getId());
        shoppingCart.getCartItems().remove(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    private ShoppingCart getCurrentShoppingCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cant find user with email: " + authentication.getPrincipal()));
        return shoppingCartRepository.findByUser(user);
    }

    private void addOrUpdateCartItem(ShoppingCart cart, CartItem newItem) {
        cart.getCartItems().stream()
                .filter(item -> Objects.equals(item.getBook().getId(),
                        newItem.getBook().getId()))
                .findFirst()
                .ifPresentOrElse(existingItem ->
                                existingItem.setQuantity(existingItem.getQuantity()
                                        + newItem.getQuantity()),
                        () -> {
                            newItem.setShoppingCart(cart);
                            cart.getCartItems().add(newItem);
                        });
    }
}
