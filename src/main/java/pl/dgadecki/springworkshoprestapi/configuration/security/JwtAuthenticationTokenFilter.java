package pl.dgadecki.springworkshoprestapi.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String BEARER_AUTHENTICATION_TYPE_KEY = "Bearer ";
    private static final String AUTHENTICATION_SECRET = "F8lFXMut39PHwpBufVcFOHxqFuOLV4+rGIGOPP3P71c=";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Parse authentication token from request
        Optional<String> authenticationToken = parseAuthenticationToken(request);

        // 2. Validate authentication token and manage authentication
        authenticationToken.ifPresent(this::manageAuthentication);

        // 3. Continue filter chain
        filterChain.doFilter(request, response);
    }

    // 1. Parse authentication token from request
    private Optional<String> parseAuthenticationToken(HttpServletRequest request) {
        final String authenticationToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authenticationToken != null && authenticationToken.startsWith(BEARER_AUTHENTICATION_TYPE_KEY)) {
            return Optional.of(authenticationToken.substring(BEARER_AUTHENTICATION_TYPE_KEY.length()));
        }
        return Optional.empty();
    }

    // 2. Validate authentication token
    private void manageAuthentication(String authenticationToken) {
        Claims claims = parseAuthenticationToken(authenticationToken);
        authenticateUser(
                retrieveUserFromClaims(claims),
                retrieveAuthoritiesFromClaims(claims)
        );
    }

    private Claims parseAuthenticationToken(String authenticationToken) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(AUTHENTICATION_SECRET.getBytes()))
                .build()
                .parseClaimsJws(authenticationToken)
                .getBody();
    }

    private String retrieveUserFromClaims(Claims claims) {
        return claims.getSubject();
    }

    private List<WorkshopAuthority> retrieveAuthoritiesFromClaims(Claims claims) {
        List<?> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Object::toString)
                .map(WorkshopAuthority::safeValueOf)
                .filter(Objects::nonNull)
                .toList();
    }

    private void authenticateUser(String principal, List<WorkshopAuthority> authorities) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /*private void authenticateUser(String principal, List<WorkshopAuthority> authorities) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "spring-workshop-user",
                null,
                List.of(WorkshopAuthority.ACCESS_TO_CUSTOMER_API, WorkshopAuthority.CREATE_CUSTOMER_AUTHORITY)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }*/
}
