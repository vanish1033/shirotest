package com.domain;

import lombok.Data;

@Data
public class RolesPermissions {
    private Long id;

    private String roleName;

    private String permission;
}