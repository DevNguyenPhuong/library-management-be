package com.example.library.cart_item;

import com.example.library.dto.CartItem.CartItemCreationRequest;
import com.example.library.dto.CartItem.CartItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    CartItem toCartItem(CartItemCreationRequest request);

    @Mapping(target = "created_at", source = "created_at")
    CartItemResponse toCartItemResponse(CartItem cartItem);
}
