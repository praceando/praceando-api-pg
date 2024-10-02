/*
 * Class: AnuncianteController
 * Description: Controller for the Anunciante entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 24/09/2024
 * Last Updated: 24/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Anunciante;
import blomera.praceando.praceandoapipg.service.AnuncianteService;
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
@RequestMapping("/api/anunciante")
@Tag(name = "Anunciante", description = "Gerenciar anunciantes")
public class AnuncianteController {
    private final AnuncianteService anuncianteService;

    @Autowired
    public AnuncianteController(AnuncianteService anuncianteService) {
        this.anuncianteService = anuncianteService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos os anunciantes", description = "Retorna uma lista de todos os anunciantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de anunciantes retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum anunciante encontrado")
    })
    public ResponseEntity<?> listarAnunciantes() {
        List<Anunciante> anunciantes = anuncianteService.getAnunciantes();
        if (anunciantes != null) {
            return ResponseEntity.ok(anunciantes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum anunciante encontrado.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um novo anunciante", description = "Adiciona um novo anunciante ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anunciante inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirAnunciante(@RequestBody Anunciante anunciante) {
        try {
            Anunciante novoAnunciante = anuncianteService.saveAnunciante(anunciante);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoAnunciante);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir anunciante.");
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca um anunciante pelo ID", description = "Retorna um anunciante pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anunciante encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anunciante não encontrado")
    })
    public ResponseEntity<?> buscarAnunciantePorId(@Parameter(description = "ID do anunciante a ser buscado") @PathVariable Long id) {
        Anunciante anunciante = anuncianteService.getAnuncianteById(id);
        if (anunciante != null) {
            return ResponseEntity.ok(anunciante);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anunciante não encontrado.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui um anunciante pelo ID", description = "Remove um anunciante pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anunciante excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anunciante não encontrado")
    })
    public ResponseEntity<?> excluirAnunciante(@Parameter(description = "ID do anunciante a ser excluído") @PathVariable Long id) {
        Anunciante anuncianteExcluido = anuncianteService.deleteAnuncianteById(id);
        if (anuncianteExcluido != null) {
            return ResponseEntity.ok("Anunciante excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anunciante não encontrado.");
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza um anunciante pelo ID", description = "Atualiza um anunciante existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anunciante atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Anunciante não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarAnunciante(@Parameter(description = "ID do anunciante a ser atualizado") @PathVariable Long id, @RequestBody Anunciante anuncianteAtualizado) {
        try {
            Anunciante anunciante = anuncianteService.updateAnunciante(id, anuncianteAtualizado);
            if (anunciante != null) {
                return ResponseEntity.ok(anunciante);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anunciante não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar anunciante.");
        }
    }
}