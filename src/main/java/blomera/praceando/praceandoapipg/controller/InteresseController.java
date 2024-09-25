/*
 * Class: InteresseController
 * Description: Controller for the Interesse entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 23/09/2024
 * Last Updated: 23/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Interesse;
import blomera.praceando.praceandoapipg.service.InteresseService;
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
@RequestMapping("/api/interesse")
@Tag(name = "Interesse", description = "Gerenciar interesses")
public class InteresseController {
    private final InteresseService interesseService;

    @Autowired
    public InteresseController(InteresseService interesseService) {
        this.interesseService = interesseService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos os interesses", description = "Retorna uma lista de todos os interesses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de interesses retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum interesse encontrado")
    })
    public ResponseEntity<?> listarInteresses() {
        List<Interesse> interesses = interesseService.getInteresses();
        if (interesses != null) {
            return ResponseEntity.ok(interesses);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum interesse encontrado.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um novo interesse", description = "Adiciona um novo interesse ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Interesse inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirInteresse(@RequestBody Interesse interesse) {
        try {
            Interesse novoInteresse = interesseService.saveInteresse(interesse);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoInteresse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir interesse.");
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca um interesse pelo ID", description = "Retorna um interesse pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interesse encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Interesse não encontrado")
    })
    public ResponseEntity<?> buscarInteressePorId(@Parameter(description = "ID do interesse a ser buscado") @PathVariable Long id) {
        Interesse interesse = interesseService.getInteresseById(id);
        if (interesse != null) {
            return ResponseEntity.ok(interesse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Interesse não encontrado.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui um interesse pelo ID", description = "Remove um interesse pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interesse excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Interesse não encontrado")
    })
    public ResponseEntity<?> excluirInteresse(@Parameter(description = "ID do interesse a ser excluído") @PathVariable Long id) {
        Interesse interesseExcluido = interesseService.deleteInteresseById(id);
        if (interesseExcluido != null) {
            return ResponseEntity.ok("Interesse excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Interesse não encontrado.");
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza um interesse pelo ID", description = "Atualiza um interesse existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interesse atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Interesse não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarInteresse(@Parameter(description = "ID do interesse a ser atualizado") @PathVariable Long id, @RequestBody Interesse interesseAtualizado) {
        try {
            Interesse interesse = interesseService.updateInteresse(id, interesseAtualizado);
            if (interesse != null) {
                return ResponseEntity.ok(interesse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Interesse não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar interesse.");
        }
    }
}
