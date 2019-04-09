package com.diasonti.descriptiontinder.config.security.jwt;

import java.util.Optional;

public final class JWTConfig {

    public static final String SECRET = Optional.ofNullable(System.getenv("JWT_SECRET")).orElse("secret");
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days

    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_NAME = "Authorization";

    private JWTConfig() {}
}
