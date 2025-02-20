package com.example.library.cart_item;

import com.example.library.dto.CartItem.CartItemCreationRequest;
import com.example.library.dto.CartItem.CartItemResponse;
import com.example.library.dto.CartItem.CartItemUpdateRequest;
import com.example.library.dto.Exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-item")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CartItemController {
    CartItemService cartItemService;
    @PostMapping
    public ApiResponse<CartItemResponse> createCartItem(@RequestBody @Valid CartItemCreationRequest request) {
        return ApiResponse.<CartItemResponse>builder()
                .result(cartItemService.createCartItem(request))
                .build();
    }

    @PutMapping("/{cartItemId}")
    public ApiResponse<CartItemResponse> updateCartItem(@PathVariable String cartItemId ,@RequestBody @Valid CartItemUpdateRequest request) {
        return ApiResponse.<CartItemResponse>builder()
                .result(cartItemService.updateCartItem(cartItemId, request))
                .build();
    }

    @DeleteMapping("/{cartItemId}")
    public ApiResponse<Void> deleteCartItem(@PathVariable String cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return ApiResponse.<Void>builder().build();
    }

}
