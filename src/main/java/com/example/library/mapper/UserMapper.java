package com.example.library.mapper;

import com.example.library.dto.request.UserCreationRequest;
import com.example.library.dto.request.UserUpdateRequest;
import com.example.library.dto.response.UserResponse;
import com.example.library.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}

