/*
 * Class: UsuarioController
 * Description: Controller for the Usuario entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 30/09/2024
 * Last Updated: 03/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Anunciante;
import blomera.praceando.praceandoapipg.model.Usuario;
import blomera.praceando.praceandoapipg.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuario", description = "Gerenciar usuários.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

//    @GetMapping("/find/{id}")
//    @Operation(summary = "Busca um anunciante pelo ID", description = "Retorna um anunciante pelo seu ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Anunciante encontrado com sucesso"),
//            @ApiResponse(responseCode = "404", description = "Anunciante não encontrado")
//    })
//    public ResponseEntity<?> buscarAnunciantePorId(@Parameter(description = "ID do anunciante a ser buscado") @PathVariable Long id) {
//        Anunciante anunciante = anuncianteService.getAnuncianteById(id);
//        if (anunciante != null) {
//            return ResponseEntity.ok(anunciante);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anunciante não encontrado.");
//        }
//    }

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
