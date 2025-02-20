package com.example.library.Shopping_session;

import com.example.library.cart_item.CartItemService;
import com.example.library.dto.CartItem.CartItemResponse;
import com.example.library.dto.Exception.ApiResponse;
import com.example.library.dto.ShoppingSession.ShoppingSessionCreationRequest;
import com.example.library.dto.ShoppingSession.ShoppingSessionResponse;
import com.example.library.dto.ShoppingSession.ShoppingSessionUpdateRequest;
import com.example.library.dto.user.UserResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shopping-session")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShoppingSessionController {
    ShoppingSessionService shoppingSessionService;
    private final CartItemService cartItemService;

    @GetMapping("/{shoppingSessionId}")
    public ApiResponse<ShoppingSessionResponse> getShoppingSession(@PathVariable String shoppingSessionId) {
        return ApiResponse.<ShoppingSessionResponse>builder()
                .result(shoppingSessionService.getShoppingSession(shoppingSessionId))
                .build();
    }



    @PostMapping("/{shoppingSessionId}")
    public ApiResponse<ShoppingSessionResponse> updateShoppingSession(@PathVariable String shoppingSessionId, @RequestBody @Valid ShoppingSessionUpdateRequest request) {
        return ApiResponse.<ShoppingSessionResponse>builder()
                .result(shoppingSessionService.updateShoppingSession(shoppingSessionId,request))
                .build();
    }

    @GetMapping("/{shoppingSessionId}/cart-items")
    public ApiResponse<List<CartItemResponse>> getCartItems(@PathVariable String shoppingSessionId) {
        return ApiResponse.<List<CartItemResponse>>builder()
                .result(cartItemService.getAllBySession(shoppingSessionId))
                .build();
    }

}
