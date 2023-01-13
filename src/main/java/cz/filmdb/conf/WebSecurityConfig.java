package cz.filmdb.conf;

import cz.filmdb.web.*;
import lombok.RequiredArgsConstructor;
import org.burningwave.core.assembler.ComponentContainer;
import org.burningwave.core.assembler.ComponentSupplier;
import org.burningwave.core.classes.ClassHunter;
import org.burningwave.core.classes.SearchConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.util.Collection;

@Configuration
// This annotation ensures that Spring Security is initialized
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    private final String[] whitelistedURLs = new String[] {
            "api/v1/auth/**"
    };

    //TODO: Alter this after getting into production!
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf()
                .disable()
                .authorizeHttpRequests()
                 //These URLs won't require authentication
                .requestMatchers("/api/v1/auth/**,")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        Annotation[] annotations = UserController.class.getAnnotations();

        return http.build();
    }

    /*
    private String[] getWhitelistedURLs() {

        ComponentSupplier componentSupplier = ComponentContainer.getInstance();
        ClassHunter classHunter = componentSupplier.getClassHunter();

        Collection<Class<?>> classes;

        try (ClassHunter.SearchResult result = classHunter.findBy(SearchConfig.forResources("cz/filmdb/web"))) {
            classes = result.getClasses();
        }

        String userUrl = UserController.class.getAnnotation(RestController.class).value();
        String genreUrl = GenreController.class.getAnnotation(RestController.class).value();
        String personUrl = PersonController.class.getAnnotation(RestController.class).value();
        String tvShowUrl = TVShowController.class.getAnnotation(RestController.class).value();
        String movieUrl = MovieController.class.getAnnotation(RestController.class).value();
        String authUrl = AuthenticationController.class.getAnnotation(RestController.class).value();
        String reviewUrl = ReviewController.class.getAnnotation(RestController.class).value();

        String[] urls = new String[] {
                userUrl,
                genreUrl,
                personUrl,
                tvShowUrl,
                movieUrl,
                reviewUrl,
        };

        for (int i = 0; i < urls.length; i++) {
            urls[i] = "/".concat(urls[i]);
        }

        return urls;
    }*/

}
