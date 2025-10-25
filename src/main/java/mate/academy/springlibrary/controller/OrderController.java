package mate.academy.springlibrary.controller;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mate.academy.springlibrary.dto.order.OrderItemResponseDto;
import mate.academy.springlibrary.dto.order.OrderRequestDto;
import mate.academy.springlibrary.dto.order.OrderResponseDto;
import mate.academy.springlibrary.dto.order.OrderUpdateStatusRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller")
public class OrderController {

    @PostMapping
    @Operation(summary = "Create new order")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    public OrderResponseDto save(
            @RequestBody OrderRequestDto requestDto) {
        return null;
    }

    @GetMapping
    @Operation(summary = "Return all orders for current user")
    @PreAuthorize("hasRole('USER')")
    public List<OrderResponseDto> getAll() {
        return List.of();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update order status")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponseDto updateStatus(
            @PathVariable Long id,
            @RequestBody OrderUpdateStatusRequestDto requestDto) {
        return null;
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Return all items for a specific order")
    @PreAuthorize("hasRole('USER')")
    public List<OrderItemResponseDto> getAllOrderItems(
            @PathVariable Long orderId) {
        return List.of();
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @Operation(summary = "Return item from a specific order")
    @PreAuthorize("hasRole('USER')")
    public OrderItemResponseDto getOrderItem(
            @PathVariable Long orderId,
            @PathVariable Long itemId) {
        return null;
    }
}
