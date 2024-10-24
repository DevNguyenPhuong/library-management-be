package com.example.library.user;

import com.example.library.dto.user.UserCreationRequest;
import com.example.library.dto.user.UserUpdateRequest;
import com.example.library.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "id", ignore = true)    // Ignore id field since it shouldn't be updated
    @Mapping(target = "password", ignore = true)  // Ignore password if you don't want to update it
    @Mapping(target = "username", ignore = true)  // Ignore username if you don't want to update it
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}

