/*
 * Class: LocalController
 * Description: Controller for the Local entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 23/09/2024
 * Last Updated: 23/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Evento;
import blomera.praceando.praceandoapipg.model.Local;
import blomera.praceando.praceandoapipg.service.LocalService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/local")
@Tag(name = "Local", description = "Gerenciar locais")
public class LocalController {
    private final LocalService localService;

    @Autowired
    public LocalController(LocalService localService) {
        this.localService = localService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos os locais", description = "Retorna uma lista de todos os locais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de locais retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum local encontrado")
    })
    public ResponseEntity<?> listarLocais() {
        List<Local> locais = localService.getLocais();
        if (locais != null) {
            return ResponseEntity.ok(locais);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum local encontrado.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um novo local", description = "Adiciona um novo local ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Local inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirLocal(@RequestBody Local local) {
        try {
            Local novoLocal = localService.saveLocal(local);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoLocal);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir local.");
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca um local pelo ID", description = "Retorna um local pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Local encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Local não encontrado")
    })
    public ResponseEntity<?> buscarLocalPorId(@Parameter(description = "ID do local a ser buscado") @PathVariable Long id) {
        Local local = localService.getLocalById(id);
        if (local != null) {
            return ResponseEntity.ok(local);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Local não encontrado.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui um local pelo ID", description = "Remove um local pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Local excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Local não encontrado")
    })
    public ResponseEntity<?> excluirLocal(@Parameter(description = "ID do local a ser excluído") @PathVariable Long id) {
        Local localExcluido = localService.deleteLocalById(id);
        if (localExcluido != null) {
            return ResponseEntity.ok("Local excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Local não encontrado.");
        }
    }

    @DeleteMapping("/soft-delete/{id}")
    @Operation(summary = "Desativa um local ao invés de removê-lo.")
    public ResponseEntity<String> softDeleteEvento(@PathVariable Long id) {
        Optional<Local> local = localService.softDelete(id);
        if (local.isPresent()) {
            return ResponseEntity.ok("Local desativado com sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza um local pelo ID", description = "Atualiza um local existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Local atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Local não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarLocal(@Parameter(description = "ID do local a ser atualizado") @PathVariable Long id, @RequestBody Local localAtualizado) {
        try {
            Local local = localService.updateLocal(id, localAtualizado);
            if (local != null) {
                return ResponseEntity.ok(local);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Local não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar local.");
        }
    }
}
