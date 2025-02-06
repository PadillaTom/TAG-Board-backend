package com.padillatom.tagboard.config;

public class SecurityConstants {

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ISSUER = "Tag-Solution";
    public static final String SECRET = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
}
