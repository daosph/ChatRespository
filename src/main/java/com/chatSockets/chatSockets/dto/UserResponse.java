package com.chatSockets.chatSockets.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Integer userId;
    private String role;

    public UserResponse(Integer userId, String role) {
        this.userId = userId;
        this.role = role;
    }
}
