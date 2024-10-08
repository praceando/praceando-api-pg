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
                        .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/anunciante/create", "/api/consumidor/create").hasRole("DESLOGADO")
                        .requestMatchers("/api/compra/**").hasAnyRole("CONSUMIDOR", "ANUNCIANTE", "CONSUMIDOR_PREMIUM", "ANUNCIANTE_PREMIUM")
                        .requestMatchers("/api/consumidor/read", "/api/consumidor/find/**", "/api/anunciante/read", "/api/anunciante/find/**").hasAnyRole("CONSUMIDOR", "ANUNCIANTE", "CONSUMIDOR_PREMIUM", "ANUNCIANTE_PREMIUM")
                        .requestMatchers("/api/evento/**").hasAnyRole( "ANUNCIANTE", "ANUNCIANTE_PREMIUM")
                        .requestMatchers("/api/evento/read", "/api/evento/find/**").hasAnyRole( "CONSUMIDOR", "CONSUMIDOR_PREMIUM")
                        .requestMatchers("/api/fraseSustentavel/read").hasAnyRole("CONSUMIDOR", "ANUNCIANTE", "CONSUMIDOR_PREMIUM", "ANUNCIANTE_PREMIUM")
                        .requestMatchers("/api/genero/read").hasAnyRole("CONSUMIDOR", "ANUNCIANTE", "CONSUMIDOR_PREMIUM", "ANUNCIANTE_PREMIUM")
                        .requestMatchers("/api/interesse/**").hasAnyRole("CONSUMIDOR", "ANUNCIANTE", "CONSUMIDOR_PREMIUM", "ANUNCIANTE_PREMIUM")
                        .requestMatchers("/api/local/read", "/api/local/find/**").hasAnyRole("CONSUMIDOR", "ANUNCIANTE", "CONSUMIDOR_PREMIUM", "ANUNCIANTE_PREMIUM")
                        .requestMatchers("/api/pagamento/**").hasAnyRole("CONSUMIDOR", "ANUNCIANTE", "CONSUMIDOR_PREMIUM", "ANUNCIANTE_PREMIUM")
                        .requestMatchers("/api/produto/read", "/api/produto/find/**").hasAnyRole("ANUNCIANTE", "ANUNCIANTE_PREMIUM")
                        .requestMatchers("/api/tag/read", "/api/tag/find/**").hasAnyRole("CONSUMIDOR", "ANUNCIANTE", "CONSUMIDOR_PREMIUM", "ANUNCIANTE_PREMIUM")
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
