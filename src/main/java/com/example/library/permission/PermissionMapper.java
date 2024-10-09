package com.example.library.permission;

import com.example.library.dto.permission.PermissionRequest;
import com.example.library.dto.permission.PermissionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
