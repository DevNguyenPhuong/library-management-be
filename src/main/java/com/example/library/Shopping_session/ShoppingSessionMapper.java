package com.example.library.Shopping_session;

import com.example.library.cart_item.CartItemMapper;
import com.example.library.dto.ShoppingSession.ShoppingSessionCreationRequest;
import com.example.library.dto.ShoppingSession.ShoppingSessionResponse;
import com.example.library.dto.ShoppingSession.ShoppingSessionUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface ShoppingSessionMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "patron", ignore = true)
    ShoppingSession toShoppingSession(ShoppingSessionCreationRequest request);


    @Mapping(target = "created_at", source = "createdAt")
    @Mapping(target = "cartItems", source = "cartItems")
    ShoppingSessionResponse toShoppingSessionResponse(ShoppingSession shoppingSession);


    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "patron", ignore = true)
    void updateShoppingSession(@MappingTarget ShoppingSession shoppingSession, ShoppingSessionUpdateRequest updateRequest);
}
