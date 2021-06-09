package com.thedariusz.warnme.twitter;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

class PasswordTest {

    @Test
    void encodePassword(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = "test";
        final String encode = encoder.encode(pass);
        System.out.println(encode);
    }

    @Test
    void yolo() {
        final OffsetDateTime now = OffsetDateTime.now();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        final String format = now.format(dateTimeFormatter);
        System.out.println(format);
    }
}
