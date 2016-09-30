package com.engagepoint.msap.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String PARENT = "PARENT";

    public static final String FOSTER_PARENT = "FOSTER_PARENT";

    public static final String CASE_WORKER = "CASE_WORKER";

    private AuthoritiesConstants() {
    }
}
