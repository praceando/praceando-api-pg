package blomera.praceando.praceandoapipg.authentication.filter;

import blomera.praceando.praceandoapipg.authentication.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService userDetailsService;
    private final SecretKey secretKey;

    public JwtAuthenticationFilter(CustomUserDetailsService userDetailsService, SecretKey secretKey) {
        this.userDetailsService = userDetailsService;
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        String requestURI = request.getRequestURI();
        List<String> publicRoutes = List.of(
                "/api/evento/.*",
                "/api/auth/.*",
                "/swagger-ui/.*",
                "/v3/api-docs/.*",
                "/api/usuario/.*",
                "/api/tag/.*",
                "/api/acesso/.*",
                "/api/evento/find/.*",
                "/api/pagamento/complete-purchase/.*",
                "/api/anunciante/.*",
                "/api/consumidor/.*"
        );

        boolean isPublicRoute = publicRoutes.stream().anyMatch(route -> requestURI.matches(route.replace("**", ".*")));

        if (isPublicRoute) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = Jwts.parser()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .getSubject();

                if (username != null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Token inválido ou expirado: " + e.getMessage() + "\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

