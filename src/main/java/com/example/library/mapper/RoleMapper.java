package com.example.library.mapper;

import com.example.library.dto.request.RoleRequest;
import com.example.library.dto.response.RoleResponse;
import com.example.library.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
