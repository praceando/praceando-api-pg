/*
 * Class: CompraController
 * Description: Controller for the Compra entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 19/09/2024
 * Last Updated: 19/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.dto.CompraRequestDTO;
import blomera.praceando.praceandoapipg.model.Compra;
import blomera.praceando.praceandoapipg.service.CompraService;
import blomera.praceando.praceandoapipg.service.EventoService;
import blomera.praceando.praceandoapipg.service.ProdutoService;
import blomera.praceando.praceandoapipg.service.UsuarioService;
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
@RequestMapping("/api/compra")
@Tag(name = "Compra", description = "Gerenciar compras")
public class CompraController {
    private final CompraService compraService;
    private final UsuarioService usuarioService;
    private final EventoService eventoService;
    private final ProdutoService produtoService;

    @Autowired
    public CompraController(CompraService compraService, UsuarioService usuarioService, EventoService eventoService, ProdutoService produtoService) {
        this.compraService = compraService;
        this.usuarioService = usuarioService;
        this.eventoService = eventoService;
        this.produtoService = produtoService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos as compras", description = "Retorna uma lista de todos as compras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de compras retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma compra encontrada")
    })
    public ResponseEntity<?> listarCompras() {
        List<Compra> compras = compraService.getCompras();
        if (compras != null) {
            return ResponseEntity.ok(compras);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma compra encontrada.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere uma nova compra", description = "Adiciona uma nova compra ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra inserida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirCompra(@RequestBody CompraRequestDTO compraRequestDTO) {
        try {
            compraService.saveCompra(
                    compraRequestDTO.getCdUsuario().intValue(),
                    compraRequestDTO.getCdProduto() != null ? compraRequestDTO.getCdProduto().intValue() : null,
                    compraRequestDTO.getCdEvento() != null ? compraRequestDTO.getCdEvento().intValue() : null,
                    compraRequestDTO.getVlTotal()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body("Compra inserida com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir compra: " + e.getMessage());
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca uma compra pelo ID", description = "Retorna uma compra pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Compra não encontrada")
    })
    public ResponseEntity<?> buscarCompraPorId(@Parameter(description = "ID da compra a ser buscada") @PathVariable Long id) {
        Compra compra = compraService.getCompraById(id);
        if (compra != null) {
            return ResponseEntity.ok(compra);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Compra não encontrada.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui uma compra pelo ID", description = "Remove uma compra pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Compra não encontrada")
    })
    public ResponseEntity<?> excluirCompra(@Parameter(description = "ID da compra a ser excluída") @PathVariable Long id) {
        Compra compraExcluido = compraService.deleteCompraById(id);
        if (compraExcluido != null) {
            return ResponseEntity.ok("Compra excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Compra não encontrada.");
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza uma compra pelo ID", description = "Atualiza uma compra existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Compra não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarCompra(@Parameter(description = "ID da compra a ser atualizada") @PathVariable Long id, @RequestBody Compra compraAtualizada) {
        try {
            Compra compra = compraService.updateCompra(id, compraAtualizada);
            if (compra != null) {
                return ResponseEntity.ok(compra);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Compra não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar compra.");
        }
    }
}
