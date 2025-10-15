package mate.academy.springlibrary.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.shoppingcart.ShoppingCartResponseDto;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.AddToCartRequestDto;
import mate.academy.springlibrary.dto.shoppingcart.cartitem.UpdateCartItemRequestDto;
import mate.academy.springlibrary.service.shoppingcart.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    @Operation(summary = "Get shopping cart for actual user")
    public ShoppingCartResponseDto getUsersShoppingCart() {
        return shoppingCartService.getCurrentUsersShoppingCart();
    }

    @PostMapping
    @Operation(summary = "Get shopping cart for actual user")
    public ShoppingCartResponseDto addBookToShoppingCart(
            @RequestBody AddToCartRequestDto cartItemDto) {
        return null;
    }

    @PutMapping("/items/{cartItemId}")
    public ShoppingCartResponseDto updateShoppingCartItem(
            @RequestParam Long cartItemId,
            @RequestBody UpdateCartItemRequestDto cartItemDto) {
        return null;
    }

    @DeleteMapping("/items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShoppingCartItem(@RequestParam Long cartItemId) {

    }




}
