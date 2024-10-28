/*
 * Class: UsuarioController
 * Description: Controller for the Usuario entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 30/09/2024
 * Last Updated: 21/10/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Anunciante;
import blomera.praceando.praceandoapipg.model.Consumidor;
import blomera.praceando.praceandoapipg.model.Usuario;
import blomera.praceando.praceandoapipg.service.AnuncianteService;
import blomera.praceando.praceandoapipg.service.ConsumidorService;
import blomera.praceando.praceandoapipg.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuario", description = "Gerenciar usuários.")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AnuncianteService anuncianteService;
    private final ConsumidorService consumidorService;

    public UsuarioController(UsuarioService usuarioService, AnuncianteService anuncianteService, ConsumidorService consumidorService) {
        this.usuarioService = usuarioService;
        this.consumidorService = consumidorService;
        this.anuncianteService = anuncianteService;
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca um usuário pelo ID", description = "Retorna um usuário pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<?> buscarUsuarioPorId(@Parameter(description = "ID do usuário a ser buscado") @PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);

        if (usuario != null) {
            Map<String, String> response = new HashMap<>();
            response.put("id", String.valueOf(usuario.getId()));

            if (usuario.getAcesso().getId() == 2) {
                Anunciante anunciante = anuncianteService.getAnuncianteById(id);
                response.put("nome", anunciante.getNmEmpresa());
            } else if (usuario.getAcesso().getId() == 1) {
                Consumidor consumidor = consumidorService.getConsumidorById(id);
                response.put("nome", consumidor.getNmNickname());
            } else {
                response.put("nome", usuario.getNmUsuario());
            }
            response.put("inventario", String.valueOf(usuario.getCdInventarioAvatar()));
            response.put("tipoUsuario", String.valueOf(usuario.getAcesso().getId()));
            response.put("bio", usuario.getDsUsuario());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    @GetMapping("/findByEmail/{email}")
    @Operation(summary = "Busca um usuário pelo e-mail", description = "Retorna um usuário pelo seu e-mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<?> buscarUsuarioPorEmail(
            @Parameter(description = "E-mail do usuário a ser buscado") @PathVariable String email) {
        Usuario usuario = usuarioService.getUsuarioByEmail(email);

        if (usuario != null) {
            Map<String, String> response = new HashMap<>();
            response.put("id", String.valueOf(usuario.getId()));

            if (usuario.getAcesso().getId() == 2) {
                Anunciante anunciante = anuncianteService.getAnuncianteById(usuario.getId());
                response.put("nome", anunciante != null ? anunciante.getNmEmpresa() : "Nome não disponível");
            } else if (usuario.getAcesso().getId() == 1) {
                Consumidor consumidor = consumidorService.getConsumidorById(usuario.getId());
                response.put("nome", consumidor != null ? consumidor.getNmNickname() : "Nome não disponível");
            } else {
                response.put("nome", usuario.getNmUsuario());
            }

            response.put("inventario", String.valueOf(usuario.getCdInventarioAvatar()));
            response.put("tipoUsuario", String.valueOf(usuario.getAcesso().getId()));
            response.put("bio", usuario.getDsUsuario());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }


    @GetMapping("/existsByEmail/{email}")
    @Operation(summary = "Verifica se o e-mail já está em uso", description = "Retorna um booleano indicando se o e-mail já existe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso"),
    })
    public ResponseEntity<?> verificarEmail(@Parameter(description = "Email do consumidor a ser verificado") @PathVariable String email) {
        boolean existe = usuarioService.existsByEmail(email);

        Map<String, Boolean> response = new HashMap<>();
        response.put("emailEmUso", existe);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/soft-delete/{id}")
    @Operation(summary = "Desativa um usuário ao invés de removê-lo.")
    public ResponseEntity<String> softDeleteUsuario(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.softDelete(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok("Usuário desativado com sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
