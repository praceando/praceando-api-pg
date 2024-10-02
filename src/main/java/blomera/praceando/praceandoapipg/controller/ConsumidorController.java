/*
 * Class: ConsumidorController
 * Description: Controller for the Consumidor entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 24/09/2024
 * Last Updated: 01/10/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Consumidor;
import blomera.praceando.praceandoapipg.service.ConsumidorService;
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
@RequestMapping("/api/consumidor")
@Tag(name = "Consumidor", description = "Gerenciar consumidores")
public class ConsumidorController {
    private final ConsumidorService consumidorService;

    @Autowired
    public ConsumidorController(ConsumidorService consumidorService) {
        this.consumidorService = consumidorService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos os consumidores", description = "Retorna uma lista de todos os consumidores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de consumidores retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum consumidor encontrado")
    })
    public ResponseEntity<?> listarConsumidores() {
        List<Consumidor> consumidores = consumidorService.getConsumidores();
        if (consumidores != null) {
            return ResponseEntity.ok(consumidores);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum consumidor encontrado.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um novo consumidor", description = "Adiciona um novo consumidor ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consumidor inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirConsumidor(@RequestBody Consumidor consumidor) {
        try {
            Consumidor novoConsumidor = consumidorService.saveConsumidor(consumidor);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoConsumidor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir consumidor.");
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca um consumidor pelo ID", description = "Retorna um consumidor pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consumidor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consumidor não encontrado")
    })
    public ResponseEntity<?> buscarConsumidorPorId(@Parameter(description = "ID do consumidor a ser buscado") @PathVariable Long id) {
        Consumidor consumidor = consumidorService.getConsumidorById(id);
        if (consumidor != null) {
            return ResponseEntity.ok(consumidor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumidor não encontrado.");
        }
    }

    @GetMapping("/existsByNickname/{nickname}")
    @Operation(summary = "Verifica se o nickname já está em uso", description = "Retorna um booleano indicando se o nickname já existe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consumidor não encontrado com esse nickname")
    })
    public ResponseEntity<?> verificarNickname(@Parameter(description = "Nickname do consumidor a ser verificado") @PathVariable String nickname) {
        boolean existe = consumidorService.existsByNickname(nickname);
        if (existe) {
            return ResponseEntity.ok("O nickname já está em uso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O nickname está disponível.");
        }
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui um consumidor pelo ID", description = "Remove um consumidor pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consumidor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consumidor não encontrado")
    })
    public ResponseEntity<?> excluirConsumidor(@Parameter(description = "ID do consumidor a ser excluído") @PathVariable Long id) {
        Consumidor consumidorExcluido = consumidorService.deleteConsumidorById(id);
        if (consumidorExcluido != null) {
            return ResponseEntity.ok("Consumidor excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumidor não encontrado.");
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza um consumidor pelo ID", description = "Atualiza um consumidor existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consumidor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consumidor não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarConsumidor(@Parameter(description = "ID do consumidor a ser atualizado") @PathVariable Long id, @RequestBody Consumidor consumidorAtualizado) {
        try {
            Consumidor consumidor = consumidorService.updateConsumidor(id, consumidorAtualizado);
            if (consumidor != null) {
                return ResponseEntity.ok(consumidor);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumidor não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar consumidor.");
        }
    }
}