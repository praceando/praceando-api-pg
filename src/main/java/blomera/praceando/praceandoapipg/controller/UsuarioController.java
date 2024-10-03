/*
 * Class: UsuarioController
 * Description: Controller for the Usuario entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 30/09/2024
 * Last Updated: 30/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Usuario;
import blomera.praceando.praceandoapipg.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuario", description = "Gerenciar usuários do sistema Praceando.")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PatchMapping("/soft-delete/{id}")
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
