package com.maverickstube.maverickshub.security.utils;

import java.util.List;

public class SecurityUtils {
    private SecurityUtils() {

    }
    public static List<String> PUBLIC_ENDPOINT = List.of("/api/v1/auth");
    public final static String JWT_PREFIX = "Bearer ";
}
