/*
 * Class: FraseSustentavelController
 * Description: Controller for the FraseSustentavel entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 23/09/2024
 * Last Updated: 23/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.FraseSustentavel;
import blomera.praceando.praceandoapipg.service.FraseSustentavelService;
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
@RequestMapping("/api/fraseSustentavel")
@Tag(name = "FraseSustentavel", description = "Gerenciar Frases Sustentáveis")
public class FraseSustentavelController {
    private final FraseSustentavelService fraseSustentavelService;

    @Autowired
    public FraseSustentavelController(FraseSustentavelService fraseSustentavelService) {
        this.fraseSustentavelService = fraseSustentavelService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos as frases sustentáveis", description = "Retorna uma lista de todos as frases sustentáveis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de frases sustentáveis retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma frase sustentável encontrada")
    })
    public ResponseEntity<?> listarFrasesSustentaveis() {
        List<FraseSustentavel> fraseSustentaveis = fraseSustentavelService.getFraseSustentaveis();
        if (fraseSustentaveis != null) {
            return ResponseEntity.ok(fraseSustentaveis);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma frase sustentável encontrada.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um nova frase sustentável", description = "Adiciona uma nova frase sustentavel ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Frase Sustentável inserida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirFraseSustentavel(@RequestBody FraseSustentavel fraseSustentavel) {
        try {
            FraseSustentavel novaFraseSustentavel = fraseSustentavelService.saveFraseSustentavel(fraseSustentavel);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaFraseSustentavel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir frase sustentável.");
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca uma frase sustentável pelo ID", description = "Retorna uma frase sustentável pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Frase Sustentável encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Frase Sustentável não encontrada")
    })
    public ResponseEntity<?> buscarFraseSustentavelPorId(@Parameter(description = "ID da frase sustentável a ser buscada") @PathVariable Long id) {
        FraseSustentavel fraseSustentavel = fraseSustentavelService.getFraseSustentavelById(id);
        if (fraseSustentavel != null) {
            return ResponseEntity.ok(fraseSustentavel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Frase Sustentável não encontrada.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui uma frase sustentável pelo ID", description = "Remove uma frase sustentável pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Frase Sustentável excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Frase Sustentável não encontrada")
    })
    public ResponseEntity<?> excluirFraseSustentavel(@Parameter(description = "ID da frase sustentável a ser excluída") @PathVariable Long id) {
        FraseSustentavel fraseSustentavelExcluido = fraseSustentavelService.deleteFraseSustentavelById(id);
        if (fraseSustentavelExcluido != null) {
            return ResponseEntity.ok("Frase Sustentável excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Frase Sustentável não encontrada.");
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza uma frase sustentável pelo ID", description = "Atualiza uma frase sustentável existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Frase Sustentável atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Frase Sustentável não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarFraseSustentavel(@Parameter(description = "ID da frase sustentável a ser atualizada") @PathVariable Long id, @RequestBody FraseSustentavel fraseSustentavelAtualizada) {
        try {
            FraseSustentavel fraseSustentavel = fraseSustentavelService.updateFraseSustentavel(id, fraseSustentavelAtualizada);
            if (fraseSustentavel != null) {
                return ResponseEntity.ok(fraseSustentavel);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Frase sustentável não encontrada.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar frase sustentável.");
        }
    }
}
