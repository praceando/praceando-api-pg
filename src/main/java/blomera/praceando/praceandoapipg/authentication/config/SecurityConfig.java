package blomera.praceando.praceandoapipg.authentication.config;

import blomera.praceando.praceandoapipg.authentication.filter.JwtAuthenticationFilter;
import blomera.praceando.praceandoapipg.authentication.service.CustomUserDetailsService;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/evento/find/**").permitAll()
                        .requestMatchers("/api/compra/**").permitAll()
                        .requestMatchers("/api/pagamento/**").permitAll()
                        .requestMatchers("/api/evento/**").permitAll()
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/usuario/**", "/api/anunciante/**", "/api/consumidor/**").permitAll()
                        .requestMatchers("/api/acesso/**").permitAll()
                        .requestMatchers("/api/acesso/create", "/api/acesso/update", "/api/acesso/delete").hasRole("ADMIN")
                        .requestMatchers("/api/fraseSustentavel/**").permitAll()
                        .requestMatchers("/api/genero/**").permitAll()
                        .requestMatchers("/api/genero/create", "/api/genero/update", "/api/genero/delete").hasRole("ADMIN")
                        .requestMatchers("/api/interesse/**").permitAll()
                        .requestMatchers("/api/local/**").permitAll()
                        .requestMatchers("/api/local/create", "/api/local/update", "/api/local/delete").hasRole("ADMIN")
                        .requestMatchers("api/produto/**").permitAll()
                        .requestMatchers("/api/produto/create", "/api/produto/update", "/api/produto/delete").hasRole("ADMIN")
                        .requestMatchers("/api/tag/**").permitAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthenticationFilter(userDetailsService, secretKey()),
                        UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("{\"error\":\"" +
                                    authException.getMessage() + "\"}");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("{\"error\":\"" +
                                    accessDeniedException.getMessage() + "\"}");
                        })
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
