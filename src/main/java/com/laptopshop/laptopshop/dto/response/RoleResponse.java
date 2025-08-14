package com.laptopshop.laptopshop.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse implements Serializable {
    String name;
    String description;
    Set<PermissionResponse> permissions;
}
