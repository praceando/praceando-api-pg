/*
 * Class: TagController
 * Description: Controller for the Tag entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 24/09/2024
 * Last Updated: 24/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.model.Tag;
import blomera.praceando.praceandoapipg.model.Usuario;
import blomera.praceando.praceandoapipg.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tag")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tags", description = "Gerenciar tags")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos as tags", description = "Retorna uma lista de todos as tags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tags retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma tag encontrada")
    })
    public ResponseEntity<?> listarTags() {
        List<Tag> tags = tagService.getTags();
        if (tags != null) {
            return ResponseEntity.ok(tags);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma tag encontrada.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um nova tag", description = "Adiciona uma nova tag ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag inserida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirTag(@RequestBody Tag tag) {
        try {
            Tag novaTag = tagService.saveTag(tag);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaTag);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir tag.");
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca uma tag pelo ID", description = "Retorna uma tag pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag não encontrada")
    })
    public ResponseEntity<?> buscarTagPorId(@Parameter(description = "ID da tag a ser buscada") @PathVariable Long id) {
        Tag tag = tagService.getTagById(id);
        if (tag != null) {
            return ResponseEntity.ok(tag);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag não encontrada.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui uma tag pelo ID", description = "Remove uma tag pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag não encontrada")
    })
    public ResponseEntity<?> excluirTag(@Parameter(description = "ID da tag a ser excluída") @PathVariable Long id) {
        Tag tagExcluido = tagService.deleteTagById(id);
        if (tagExcluido != null) {
            return ResponseEntity.ok("Tag excluída com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag não encontrada.");
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza uma tag pelo ID", description = "Atualiza uma tag existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tag não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarTag(@Parameter(description = "ID da tag a ser atualizada") @PathVariable Long id, @RequestBody Tag tagAtualizada) {
        try {
            Tag tag = tagService.updateTag(id, tagAtualizada);
            if (tag != null) {
                return ResponseEntity.ok(tag);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar tag.");
        }
    }

    @DeleteMapping("/soft-delete/{id}")
    @Operation(summary = "Desativa uma tag ao invés de removê-la.")
    public ResponseEntity<String> softDeleteTag(@PathVariable Long id) {
        Optional<Tag> tag = tagService.softDelete(id);
        if (tag.isPresent()) {
            return ResponseEntity.ok("Tag desativada com sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
