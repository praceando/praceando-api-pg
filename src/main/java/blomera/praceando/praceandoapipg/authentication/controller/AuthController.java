package blomera.praceando.praceandoapipg.authentication.controller;

import blomera.praceando.praceandoapipg.authentication.model.LoginRequest;
import blomera.praceando.praceandoapipg.model.Acesso;
import blomera.praceando.praceandoapipg.model.Usuario;
import blomera.praceando.praceandoapipg.service.AcessoService;
import blomera.praceando.praceandoapipg.service.UsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Gerenciar autenticação")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public final SecretKey secretKey;
    private final UsuarioService userService;
    private final AcessoService roleService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioService userService, AcessoService roleService,
                          PasswordEncoder passwordEncoder, SecretKey secretKey) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = secretKey;
    }

    @Operation(summary = "Autenticação de Usuário", description = "Efetua a autenticação do usuário e retorna um token JWT, permitindo acesso seguro aos serviços da API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticação realizada com sucesso. O token JWT foi gerado.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Falha na autenticação. As credenciais fornecidas são inválidas.",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno ao tentar gerar o token JWT. Tente novamente mais tarde.",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario user = userService.getUsuarioByEmail(loginRequest.getDsEmail());

        if (user != null && passwordEncoder.matches(loginRequest.getDsSenha(), user.getDsSenha())) {
            Acesso role = roleService.getAcessoById(user.getAcesso().getIdAcesso());
            try {
                String token = Jwts.builder()
                        .setSubject(loginRequest.getDsEmail()) // Identificador do usuário
                        .claim("user_role", role.getNmAcesso()) // Informação adicional (papel do usuário)
                        .setExpiration(new Date(System.currentTimeMillis() + 86_400_000)) // Expiração em 1 dia
                        .signWith(secretKey, SignatureAlgorithm.HS512)
                        .compact(); // Finaliza a construção e egera a string compactada do JWT

                return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", "Bearer " + token));
            } catch (Exception e) {
                logger.error("Erro ao gerar o token JWT: ", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao gerar o token JWT");
            }
        } else {
            logger.error("Credenciais inválidas para: {}", loginRequest.getDsEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }

    @Operation(summary = "Mantém a API viva", description = "Mentém a API viva usando o site https://keepalive.dashdashhard.com.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content)
    })
    @GetMapping("/keep-alive")
    public ResponseEntity<?> keepAlive() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
