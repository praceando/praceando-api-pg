/*
 * Class: EventoController
 * Description: Controller for the Evento entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 20/09/2024
 * Last Updated: 23/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.dto.EventoRequest;
import blomera.praceando.praceandoapipg.model.Evento;
import blomera.praceando.praceandoapipg.model.Produto;
import blomera.praceando.praceandoapipg.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evento")
@Tag(name = "Evento", description = "Gerenciar eventos")
public class EventoController {
    private final EventoService eventoService;

    @Autowired
    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos os eventos", description = "Retorna uma lista de todos os eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado")
    })
    public ResponseEntity<?> listarEventos() {
        List<Evento> eventos = eventoService.getEventos();
        if (eventos != null) {
            return ResponseEntity.ok(eventos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum evento encontrado.");
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Insere um novo evento", description = "Adiciona um novo evento ao sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento inserido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> inserirEvento(@RequestBody EventoRequest eventoRequest) {
        try {
            eventoService.saveEvento(eventoRequest.getEvento(), eventoRequest.getTags());
            return ResponseEntity.status(HttpStatus.CREATED).body("Evento inserido com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao inserir evento." + e.getMessage());
        }
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Busca um evento pelo ID", description = "Retorna um evento pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<?> buscarEventoPorId(@Parameter(description = "ID do evento a ser buscado") @PathVariable Long id) {
        Evento evento = eventoService.getEventoById(id);
        if (evento != null) {
            return ResponseEntity.ok(evento);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento não encontrado.");
        }
    }

    @GetMapping("/findByTag/{tagId}")
    @Operation(summary = "Busca eventos por Tag", description = "Retorna uma lista de eventos associados a uma tag específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado para esta tag")
    })
    public ResponseEntity<?> buscarEventosPorTag(@Parameter(description = "ID da tag") @PathVariable Long tagId) {
        List<Evento> eventos = eventoService.findEventosByTag(tagId);
        if (!eventos.isEmpty()) {
            return ResponseEntity.ok(eventos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum evento encontrado para esta tag.");
        }
    }

    @GetMapping("/findByAnunciante/{anuncianteId}")
    @Operation(summary = "Busca eventos por Anunciante", description = "Retorna uma lista de eventos associados a um anunciante específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado para este anunciante")
    })
    public ResponseEntity<?> buscarEventosPorAnunciante(@Parameter(description = "ID do anunciante") @PathVariable Long anuncianteId) {
        List<Evento> eventos = eventoService.findEventosByAnunciante(anuncianteId);
        if (!eventos.isEmpty()) {
            return ResponseEntity.ok(eventos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum evento encontrado para este anunciante.");
        }
    }

    @GetMapping("/findByDateRange")
    @Operation(summary = "Busca eventos por intervalo de datas", description = "Retorna uma lista de eventos que ocorreram entre duas datas específicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado para este intervalo de datas")
    })
    public ResponseEntity<?> buscarEventosPorIntervaloDeDatas(
            @Parameter(description = "Data de início") @RequestParam LocalDateTime dtInicio,
            @Parameter(description = "Data de fim") @RequestParam LocalDateTime dtFim) {
        List<Evento> eventos = eventoService.findEventosByDateRange(dtInicio, dtFim);
        if (!eventos.isEmpty()) {
            return ResponseEntity.ok(eventos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum evento encontrado para este intervalo de datas.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Exclui um evento pelo ID", description = "Remove um evento pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<?> excluirEvento(@Parameter(description = "ID do evento a ser excluído") @PathVariable Long id) {
        Evento eventoExcluido = eventoService.deleteEventoById(id);
        if (eventoExcluido != null) {
            return ResponseEntity.ok("Evento excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento não encontrado.");
        }
    }

    @DeleteMapping("/soft-delete/{id}")
    @Operation(summary = "Desativa um evento ao invés de removê-lo.")
    public ResponseEntity<String> softDeleteEvento(@PathVariable Long id) {
        Optional<Evento> evento = eventoService.softDelete(id);
        if (evento.isPresent()) {
            return ResponseEntity.ok("Evento desativado com sucesso.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Atualiza um evento pelo ID", description = "Atualiza um evento existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> atualizarEvento(@Parameter(description = "ID do evento a ser atualizado") @PathVariable Long id, @RequestBody Evento eventoAtualizado) {
        try {
            Evento evento = eventoService.updateEvento(id, eventoAtualizado);
            if (evento != null) {
                return ResponseEntity.ok(evento);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar evento.");
        }
    }
}
