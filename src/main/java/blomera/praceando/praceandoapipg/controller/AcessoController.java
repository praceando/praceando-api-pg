/*
 * Class: AcessoController
 * Description: Controller for the Acesso entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 02/10/2024
 * Last Updated: 03/10/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Acesso;
import blomera.praceando.praceandoapipg.service.AcessoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acesso")
@Tag(name = "Acesso", description = "Gerenciar acessos")
public class AcessoController {
    private final AcessoService acessoService;

    @Autowired
    public AcessoController(AcessoService acessoService) {
        this.acessoService = acessoService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos os acessos", description = "Retorna uma lista de todos os acessos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de acessos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum acesso encontrado")
    })
    public ResponseEntity<?> listarAcessos() {
        List<Acesso> acessos = acessoService.getAcessos();
        if (acessos != null) {
            return ResponseEntity.ok(acessos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum acesso encontrado.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um novo acesso", description = "Adiciona um novo acesso ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Acesso inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirAcesso(@RequestBody Acesso acesso) {
        try {
            Acesso novoAcesso = acessoService.saveAcesso(acesso);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAcesso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir acesso.");
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca um acesso pelo ID", description = "Retorna um acesso pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acesso encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Acesso não encontrado")
    })
    public ResponseEntity<?> buscarAcessoPorId(@Parameter(description = "ID do acesso a ser buscado") @PathVariable Long id) {
        Acesso acesso = acessoService.getAcessoById(id);
        if (acesso != null) {
            return ResponseEntity.ok(acesso);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Acesso não encontrado.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui um acesso pelo ID", description = "Remove um acesso pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acesso excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Acesso não encontrado")
    })
    public ResponseEntity<?> excluirAcesso(@Parameter(description = "ID do acesso a ser excluído") @PathVariable Long id) {
        Acesso acessoExcluido = acessoService.deleteAcessoById(id);
        if (acessoExcluido != null) {
            return ResponseEntity.ok("Acesso excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Acesso não encontrado.");
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza um acesso pelo ID", description = "Atualiza um acesso existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acesso atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Acesso não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarAcesso(@Parameter(description = "ID do acesso a ser atualizado") @PathVariable Long id, @RequestBody Acesso acessoAtualizado) {
        try {
            Acesso acesso = acessoService.updateAcesso(id, acessoAtualizado);
            if (acesso != null) {
                return ResponseEntity.ok(acesso);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Acesso não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar acesso.");
        }
    }
}
