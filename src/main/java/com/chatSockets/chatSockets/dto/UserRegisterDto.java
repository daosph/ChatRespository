package com.chatSockets.chatSockets.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRegisterDto {

    String lastName;
    String name;
    String password;
    String telephone;
    String user;
    Integer rol;
}
