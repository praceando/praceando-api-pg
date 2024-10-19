/*
 * Class: PagamentoController
 * Description: Controller for the Pagamento entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 23/09/2024
 * Last Updated: 24/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Pagamento;
import blomera.praceando.praceandoapipg.service.PagamentoService;
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
@RequestMapping("/api/pagamento")
@Tag(name = "Pagamento", description = "Gerenciar pagamentos")
public class PagamentoController {
    private final PagamentoService pagamentoService;

    @Autowired
    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos os pagamentos", description = "Retorna uma lista de todos os pagamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pagamentos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum pagamento encontrado")
    })
    public ResponseEntity<?> listarPagamentos() {
        List<Pagamento> pagamentos = pagamentoService.getPagamentos();
        if (pagamentos != null) {
            return ResponseEntity.ok(pagamentos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum pagamento encontrado.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um novo pagamento", description = "Adiciona um novo pagamento ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pagamento inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirPagamento(@RequestBody Pagamento pagamento) {
        try {
            Pagamento novoPagamento = pagamentoService.savePagamento(pagamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPagamento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir pagamento.");
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca um pagamento pelo ID", description = "Retorna um pagamento pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<?> buscarPagamentoPorId(@Parameter(description = "ID do pagamento a ser buscado") @PathVariable Long id) {
        Pagamento pagamento = pagamentoService.getPagamentoById(id);
        if (pagamento != null) {
            return ResponseEntity.ok(pagamento);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui um pagamento pelo ID", description = "Remove um pagamento pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado")
    })
    public ResponseEntity<?> excluirPagamento(@Parameter(description = "ID do pagamento a ser excluído") @PathVariable Long id) {
        Pagamento pagamentoExcluido = pagamentoService.deletePagamentoById(id);
        if (pagamentoExcluido != null) {
            return ResponseEntity.ok("Pagamento excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado.");
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza um pagamento pelo ID", description = "Atualiza um pagamento existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarPagamento(@Parameter(description = "ID do pagamento a ser atualizado") @PathVariable Long id, @RequestBody Pagamento pagamentoAtualizado) {
        try {
            Pagamento pagamento = pagamentoService.updatePagamento(id, pagamentoAtualizado);
            if (pagamento != null) {
                return ResponseEntity.ok(pagamento);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pagamento não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar pagamento.");
        }
    }

    @PostMapping("/complete-purchase/{cdCompra}")
    @Operation(summary = "Completa uma compra", description = "Atualiza o status de uma compra e registra o pagamento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra completada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao completar a compra"),
            @ApiResponse(responseCode = "404", description = "Compra não encontrada")
    })
    public ResponseEntity<?> completarCompra(@PathVariable Long cdCompra) {
        try {
            pagamentoService.completePurchase(cdCompra);
            return ResponseEntity.status(HttpStatus.OK).body("Compra completada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao completar compra: " + e.getMessage());
        }
    }
}
