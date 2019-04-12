package com.diasonti.descriptiontinder.config.security.jwt;

import java.util.Optional;

public final class JWTConfig {

    public static final String SECRET = Optional.ofNullable(System.getenv("JWT_SECRET")).orElse("secret");
    public static final long TEN_DAYS = 864_000_000L; // 10 days
    public static final long ONE_YEAR = 31_540_000_000L; // 10 days

    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_NAME = "Authorization";

    private JWTConfig() {}
}
