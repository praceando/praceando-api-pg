/*
 * Class: GeneroController
 * Description: Controller for the Genero entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 23/09/2024
 * Last Updated: 23/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Genero;
import blomera.praceando.praceandoapipg.service.GeneroService;
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
@RequestMapping("/api/genero")
@Tag(name = "Genero", description = "Gerenciar generos")
public class GeneroController {
    private final GeneroService generoService;

    @Autowired
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos os generos", description = "Retorna uma lista de todos os generos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de generos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum genero encontrado")
    })
    public ResponseEntity<?> listarGeneros() {
        List<Genero> generos = generoService.getGeneros();
        if (generos != null) {
            return ResponseEntity.ok(generos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum genero encontrado.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um novo genero", description = "Adiciona um novo genero ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genero inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirGenero(@RequestBody Genero genero) {
        try {
            Genero novoGenero = generoService.saveGenero(genero);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoGenero);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir genero.");
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca um genero pelo ID", description = "Retorna um genero pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genero encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Genero não encontrado")
    })
    public ResponseEntity<?> buscarGeneroPorId(@Parameter(description = "ID do genero a ser buscado") @PathVariable Long id) {
        Genero genero = generoService.getGeneroById(id);
        if (genero != null) {
            return ResponseEntity.ok(genero);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genero não encontrado.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui um genero pelo ID", description = "Remove um genero pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genero excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Genero não encontrado")
    })
    public ResponseEntity<?> excluirGenero(@Parameter(description = "ID do genero a ser excluído") @PathVariable Long id) {
        Genero generoExcluido = generoService.deleteGeneroById(id);
        if (generoExcluido != null) {
            return ResponseEntity.ok("Genero excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genero não encontrado.");
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza um genero pelo ID", description = "Atualiza um genero existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genero atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Genero não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarGenero(@Parameter(description = "ID do genero a ser atualizado") @PathVariable Long id, @RequestBody Genero generoAtualizado) {
        try {
            Genero genero = generoService.updateGenero(id, generoAtualizado);
            if (genero != null) {
                return ResponseEntity.ok(genero);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genero não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar genero.");
        }
    }
}
