package com.example.library.mapper;

import com.example.library.dto.request.PermissionRequest;
import com.example.library.dto.response.PermissionResponse;
import com.example.library.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
