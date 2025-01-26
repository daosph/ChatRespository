package com.chatSockets.chatSockets.configuration;

import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ToString
public class EncodePaswords {

    public String encodeUserPasword(String Password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String encodedPassword = encoder.encode(Password);

        return encodedPassword;
    }
}
