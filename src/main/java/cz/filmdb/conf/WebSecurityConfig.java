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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf()
            .disable()
                .authorizeHttpRequests()
                //These URLs won't require authentication
                .requestMatchers(HttpMethod.GET,
                        "/api/v1/users/**",
                        "/api/v1/users",
                        "/api/v1/movies/**",
                        "/api/v1/movies",
                        "/api/v1/tvshows/**",
                        "/api/v1/tvshows",
                        "/api/v1/reviews/**",
                        "/api/v1/reviews",
                        "/api/v1/people/**",
                        "/api/v1/people",
                        "/api/v1/genres/**",
                        "/api/v1/genres",
                        "/api/v1/files/**",
                        "/api/v1/occupations/**"
                        )
                .permitAll()
            .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/**")
                .permitAll()
            .and()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/v1/account/**",
                        "/api/v1/account"
                )
                .hasAnyRole(UserRole.USER.name(),UserRole.ADMIN.name())
            .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,
                        "/api/v1/users/**",
                        "/api/v1/users",
                        "/api/v1/movies/**",
                        "/api/v1/movies",
                        "/api/v1/tvshows/**",
                        "/api/v1/tvshows",
                        "/api/v1/reviews/**",
                        "/api/v1/reviews",
                        "/api/v1/people/**",
                        "/api/v1/people",
                        "/api/v1/genres/**",
                        "/api/v1/genres",
                        "/api/v1/files/**" // TODO: Change the permissions later on. this is just for testing purposes
                )
                .hasAnyRole(UserRole.ADMIN.name())
            .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.PUT,
                        "/api/v1/users/**",
                        "/api/v1/users",
                        "/api/v1/movies/**",
                        "/api/v1/movies",
                        "/api/v1/tvshows/**",
                        "/api/v1/tvshows",
                        "/api/v1/reviews/**",
                        "/api/v1/reviews",
                        "/api/v1/people/**",
                        "/api/v1/people",
                        "/api/v1/genres/**",
                        "/api/v1/genres"
                )
                .hasAnyRole(UserRole.ADMIN.name())
            .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.DELETE,
                        "/api/v1/users/**",
                        "/api/v1/users",
                        "/api/v1/movies/**",
                        "/api/v1/movies",
                        "/api/v1/tvshows/**",
                        "/api/v1/tvshows",
                        "/api/v1/reviews/**",
                        "/api/v1/reviews",
                        "/api/v1/people/**",
                        "/api/v1/people",
                        "/api/v1/genres/**",
                        "/api/v1/genres"
                )
                .hasAnyRole(UserRole.ADMIN.name())
                .anyRequest()
                .authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();

        return http.build();
    }

    /*
    @Bean
    @Order(1)
    public SecurityFilterChain filterChainPublic(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                //These URLs won't require authentication
                .requestMatchers(HttpMethod.GET,
                        "/api/v1/users/**",
                        "/api/v1/users",
                        "/api/v1/movies/**",
                        "/api/v1/movies",
                        "/api/v1/tvshows/**",
                        "/api/v1/tvshows",
                        "/api/v1/reviews/**",
                        "/api/v1/reviews",
                        "/api/v1/people/**",
                        "/api/v1/people",
                        "/api/v1/genres/**",
                        "/api/v1/genres",
                        "/api/v1/files/**",
                        "/api/v1/occupations/**"

                )
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,
                        "/api/v1/auth/**",
                        "/api/v1/files/**" // TODO: Change the permissions later on. this is just for testing purposes
                )
                .permitAll()

                .cors();
        return http.build();
    }*/

    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .authorizeHttpRequests()
                //These URLs won't require authentication
                .requestMatchers(
                        "/api/v1/users/**",
                        "/api/v1/users",
                        "/api/v1/movies/**",
                        "/api/v1/movies",
                        "/api/v1/tvshows/**",
                        "/api/v1/tvshows",
                        "/api/v1/reviews/**",
                        "/api/v1/reviews",
                        "/api/v1/people/**",
                        "/api/v1/people",
                        "/api/v1/genres/**",
                        "/api/v1/genres",
                        "/api/v1/files/**",
                        "/api/v1/occupations/**"
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();

        return http.build();
    }*/
}
