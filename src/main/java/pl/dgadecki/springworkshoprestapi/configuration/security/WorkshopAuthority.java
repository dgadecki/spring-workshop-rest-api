package pl.dgadecki.springworkshoprestapi.configuration.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Stream;

public enum WorkshopAuthority implements GrantedAuthority {

    ACCESS_TO_CUSTOMER_API,
    ACCESS_TO_ARTICLE_API,
    CREATE_CUSTOMER_AUTHORITY;

    @Override
    public String getAuthority() {
        return name().toUpperCase();
    }

    public static WorkshopAuthority safeValueOf(String key) {
        return Stream.of(WorkshopAuthority.values())
                .filter(authority -> authority.name().equalsIgnoreCase(key))
                .findFirst()
                .orElse(null);
    }
}
