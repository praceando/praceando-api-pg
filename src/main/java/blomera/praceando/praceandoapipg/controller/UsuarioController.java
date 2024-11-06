/*
 * Class: UsuarioController
 * Description: Controller for the Usuario entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 30/09/2024
 * Last Updated: 21/10/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.authentication.model.LoginRequest;
import blomera.praceando.praceandoapipg.dto.InventarioRequestDTO;
import blomera.praceando.praceandoapipg.dto.UsuarioDTO;
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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PutMapping("/add-inventory")
    @Operation(summary = "Adiciona um inventário a um usuário", description = "Adiciona o código de inventário do avatar a um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> adicionarInventarioUsuario(@RequestBody InventarioRequestDTO inventarioRequestDTO) {
        try {
            Integer cdUsuario = inventarioRequestDTO.getCdUsuario().intValue();
            Integer cdAvatar = inventarioRequestDTO.getCdAvatar().intValue();

            usuarioService.setInventory(cdUsuario, cdAvatar);

            return ResponseEntity.ok("Inventário do usuário atualizado com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar inventário do usuário: " + e.getMessage());
        }
    }

    @PutMapping("/update-profile")
    @Operation(summary = "Atualiza um usuário pelo ID", description = "Atualiza o nome e a bio de um usuário existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarUsuario(@RequestBody UsuarioDTO usuarioAtualizado) {
        try {
            Usuario usuario = usuarioService.getUsuarioById(usuarioAtualizado.getId());

            if (usuario.getAcesso().getId() == 2) {
                Anunciante anunciante = anuncianteService.updateAnunciante(usuarioAtualizado.getId(), usuarioAtualizado.getName(), usuarioAtualizado.getBio());

                if (anunciante != null) {
                    return ResponseEntity.ok(anunciante);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anunciante não encontrado.");
                }
            } else if (usuario.getAcesso().getId() == 1) {
                Consumidor consumidor = consumidorService.updateConsumidor(usuarioAtualizado.getId(), usuarioAtualizado.getName(), usuarioAtualizado.getBio());

                if (consumidor != null) {
                    return ResponseEntity.ok(consumidor);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumidor não encontrado.");
                }
            }

            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar usuário.");
        }
    }

    @CrossOrigin(origins = {"https://praceando-area-restrita.onrender.com", "http://127.0.0.1:5500"})
    @PostMapping("/login")
    @Operation(summary = "Login de administrador", description = "Permite que um administrador faça login na plataforma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, admin access granted."),
            @ApiResponse(responseCode = "403", description = "Access denied: Only admins can log in.")
    })
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getDsEmail();
        String password = loginRequest.getDsSenha();

        if (usuarioService.authenticateAdmin(email, password)) {
            return ResponseEntity.ok("Login successful, admin access granted.");
        } else {
            return ResponseEntity.status(403).body("Access denied: Only admins can log in.");
        }
    }
}
