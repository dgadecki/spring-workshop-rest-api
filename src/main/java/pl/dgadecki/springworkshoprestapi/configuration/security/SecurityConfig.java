package pl.dgadecki.springworkshoprestapi.configuration.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] API_DOCUMENTATION_PATHS = new String[]{
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    /*@Bean
    public SecurityFilterChain basicAuthenticationSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(http -> http.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }*/

    /*@Bean
    public SecurityFilterChain basicAuthenticationSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(http -> http
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/**").hasAuthority("CREATE_CUSTOMER_AUTHORITY")
                        .requestMatchers("/api/v1/customers/**").hasAuthority("ACCESS_TO_CUSTOMER_API")
                        .requestMatchers("/api/v1/articles/**").hasAuthority("ACCESS_TO_ARTICLE_API")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }*/

    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails firstUser = User.withUsername("spring-workshop-user")
                .password("spring-workshop-password")
                .build();

        UserDetails secondUser = User.withUsername("vmpl-user")
                .password("vmpl-password")
                .build();

        return new InMemoryUserDetailsManager(firstUser, secondUser);
    }

    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }*/

   /* @Bean
    public UserDetailsService userDetailsService() {
        UserDetails firstUser = User.withUsername("spring-workshop-user")
                .password(new BCryptPasswordEncoder().encode("spring-workshop-password"))
                .authorities("ACCESS_TO_CUSTOMER_API", "CREATE_CUSTOMER_AUTHORITY")
                .build();

        UserDetails secondUser = User.withUsername("vmpl-user")
                .password(new BCryptPasswordEncoder().encode("vmpl-password"))
                .authorities("ACCESS_TO_ARTICLE_API")
                .build();

        return new InMemoryUserDetailsManager(firstUser, secondUser);
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

    // ------------------------- Bearer Authentication -------------------------
    @Bean
    public SecurityFilterChain bearerAuthenticationSecurityFilterChain(HttpSecurity httpSecurity,
                                                                       JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(http -> http
                        .requestMatchers(API_DOCUMENTATION_PATHS).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/customers/**").hasAuthority("CREATE_CUSTOMER_AUTHORITY")
                        .requestMatchers("/api/v1/customers/**").hasAuthority("ACCESS_TO_CUSTOMER_API")
                        .requestMatchers("/api/v1/articles/**").hasAuthority("ACCESS_TO_ARTICLE_API")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((httpServletRequest, httpServletResponse, authenticationException) -> {
                            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            httpServletResponse.setContentType("application/json");
                            httpServletResponse.getWriter().write("{\"message\": \"Unauthorized\"}");
                        })
                        .accessDeniedHandler((httpServletRequest, httpServletResponse, accessDeniedException) -> {
                            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            httpServletResponse.setContentType("application/json");
                            httpServletResponse.getWriter().write("{\"message\": \"Forbidden\"}");
                        })
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
