package cz.filmdb.conf;

import cz.filmdb.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
// This annotation ensures that Spring Security is initialized
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainMovie(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf()
                .disable()
                .securityMatcher("/api/v1/movies/**",
                        "/api/v1/people/**",
                        "/api/v1/genres/**",
                        "/api/v1/occupations/**",
                        "/api/v1/users/**",
                        "/api/v1/tvshows/**"
                )
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET).permitAll()
                .requestMatchers(HttpMethod.POST).hasAuthority(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE).hasAuthority(UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.PUT).hasAuthority(UserRole.ADMIN.name())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf()
                .disable()
                .securityMatcher("/api/v1/auth/**")
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST).permitAll();
        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain filterChainPerson(HttpSecurity http) throws Exception {
        http.cors();
        // This option defines if the request can be sent from a different form than its origin.
        // It enables us to use Postman without errors.
        http.csrf()
                .disable()
                .securityMatcher("/api/v1/account/**")
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST).hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE).hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                .and()
                //We have to provide the authentication for every separate filter chain!
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    @Order(4)
    public SecurityFilterChain filterChainReviews(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf()
                .disable()
                .securityMatcher(
                        "/api/v1/reviews/**",
                        "/api/v1/files/**"
                )
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET).permitAll()
                .requestMatchers(HttpMethod.POST).hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.DELETE).hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                .requestMatchers(HttpMethod.PUT).hasAnyAuthority(UserRole.USER.name(), UserRole.ADMIN.name())
                .and()
                //We have to provide the authentication for every separate filter chain!
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /*
    @Bean
    public SecurityFilterChain filterChainMovie(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf()
                .disable()
                .securityMatcher("/api/v1/movies/**",
                        "/api/v1/people/**",
                        "/api/v1/genres/**",
                        "/api/v1/occupations/**",
                        "/api/v1/users/**",
                        "/api/v1/tvshows/**",
                        "/api/v1/reviews/**",
                        "/api/v1/files/**",
                        "/api/v1/account/**",
                        "/api/v1/auth/**"
                )
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET).permitAll()
                .requestMatchers(HttpMethod.POST).permitAll()
                .requestMatchers(HttpMethod.DELETE).permitAll()
                .requestMatchers(HttpMethod.PUT).permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }*/
}

