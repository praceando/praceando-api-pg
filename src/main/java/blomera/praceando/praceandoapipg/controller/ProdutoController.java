/*
 * Class: ProdutoController
 * Description: Controller for the Product entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 29/08/2024
 * Last Updated: 01/10/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Produto;
import blomera.praceando.praceandoapipg.model.Usuario;
import blomera.praceando.praceandoapipg.service.ProdutoService;
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
@RequestMapping("/api/products")
@Tag(name = "Produto", description = "Gerenciar produtos")
public class ProdutoController {

    private final ProdutoService produtoService;


    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


    @GetMapping("/read")
    @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista de todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado")
    })
    public ResponseEntity<?> listarProdutos() {
        List<Produto> produtos = produtoService.getProdutos();
        if (produtos != null) {
            return ResponseEntity.ok(produtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum produto encontrado.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um novo produto", description = "Adiciona um novo produto ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirProduto(@RequestBody Produto produto) {
        try {
            Produto novaProduto = produtoService.saveProduto(produto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaProduto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir produto.");
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca um produto pelo ID", description = "Retorna um produto pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<?> buscarProdutoPorId(@Parameter(description = "ID do produto a ser buscado") @PathVariable Long id) {
        Produto produto = produtoService.getProdutoById(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui um produto pelo ID", description = "Remove um produto pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<?> excluirProduto(@Parameter(description = "ID do produto a ser excluído") @PathVariable Long id) {
        Produto produtoExcluido = produtoService.deleteProdutoById(id);
        if (produtoExcluido != null) {
            return ResponseEntity.ok("Produto excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
    }

    @PatchMapping("/soft-delete/{id}")
    @Operation(summary = "Desativa um produto ao invés de removê-lo.")
    public ResponseEntity<String> softDeleteProduto(@PathVariable Long id) {
        Optional<Produto> produto = produtoService.softDelete(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok("Produto desativado com sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza um produto pelo ID", description = "Atualiza um produto existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarProduto(@Parameter(description = "ID do produto a ser atualizado") @PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        try {
            Produto produto = produtoService.updateProduto(id, produtoAtualizado);
            if (produto != null) {
                return ResponseEntity.ok(produto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar produto.");
        }
    }
}

