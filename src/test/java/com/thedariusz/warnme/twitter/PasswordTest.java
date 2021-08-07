package com.thedariusz.warnme.twitter;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordTest {

    @Test
    void encodePassword(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = "test";
        final String encode = encoder.encode(pass);
        System.out.println(encode);
    }

}
