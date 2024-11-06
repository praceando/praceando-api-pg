/*
 * Class: EventoController
 * Description: Controller for the Evento entity.
 * Author: Camilla Ucci de Menezes
 * Creation Date: 20/09/2024
 * Last Updated: 23/09/2024
 */
package blomera.praceando.praceandoapipg.controller;

import blomera.praceando.praceandoapipg.dto.EventoDTO;
import blomera.praceando.praceandoapipg.dto.EventoRequest;
import blomera.praceando.praceandoapipg.dto.InteresseRequestDTO;
import blomera.praceando.praceandoapipg.model.*;
import blomera.praceando.praceandoapipg.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/evento")
@Tag(name = "Evento", description = "Gerenciar eventos")
public class EventoController {
    private final EventoService eventoService;
    private final LocalService localService;
    private final AnuncianteService anuncianteService;
    private final UsuarioTagService usuarioTagService;

    @Autowired
    public EventoController(EventoService eventoService, LocalService localService, AnuncianteService anuncianteService, UsuarioTagService usuarioTagService) {
        this.eventoService = eventoService;
        this.localService = localService;
        this.anuncianteService = anuncianteService;
        this.usuarioTagService = usuarioTagService;
    }

    @GetMapping("/read")
    @Operation(summary = "Lista todos os eventos", description = "Retorna uma lista de todos os eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado")
    })
    public ResponseEntity<?> listarEventos() {
        List<EventoDTO> eventos = eventoService.getEventos();
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
    public ResponseEntity<?> inserirEvento(@RequestBody
                                               @Schema(example = "{\n" +
                                                       "  \"evento\": {\n" +
                                                       "    \"local\": {\n" +
                                                       "      \"id\": 1\n" +
                                                       "    },\n" +
                                                       "    \"anunciante\": {\n" +
                                                       "      \"id\": 0\n" +
                                                       "    },\n" +
                                                       "    \"nmEvento\": \"Festival de Sustentabilidade\",\n" +
                                                       "    \"dsEvento\": \"Um evento que promove práticas sustentáveis.\",\n" +
                                                       "    \"dtInicio\": \"2024-09-01\",\n" +
                                                       "    \"hrInicio\": \"20:00:00\",\n" +
                                                       "    \"dtFim\": \"2024-09-02\",\n" +
                                                       "    \"hrFim\": \"20:00:00\",\n" +
                                                       "    \"urlDocumentacao\": \"https://www.exemplo.com/documentacao\"\n" +
                                                       "  },\n" +
                                                       "  \"tags\": [\n" +
                                                       "    \"string\"\n" +
                                                       "  ]\n" +
                                                       "}") EventoRequest eventoRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Local local = localService.getLocalById(eventoRequest.getEvento().getLocal().getId());
            eventoRequest.getEvento().setLocal(local);

            Anunciante anunciante = anuncianteService.getAnuncianteById(eventoRequest.getEvento().getAnunciante().getId());
            eventoRequest.getEvento().setAnunciante(anunciante);

            Integer idEvento = eventoService.saveEvento(eventoRequest.getEvento(), eventoRequest.getTags());
            response.put("message", "Evento inserido com sucesso");
            response.put("idEvento", idEvento);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
        List<EventoDTO> eventos = eventoService.findEventosByTag(tagId);
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
        List<EventoDTO> eventos = eventoService.findEventosByAnunciante(anuncianteId);
        if (!eventos.isEmpty()) {
            return ResponseEntity.ok(eventos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum evento encontrado para este anunciante.");
        }
    }

    @GetMapping("/findByDate")
    @Operation(summary = "Busca eventos por de data", description = "Retorna uma lista de eventos que estaram ativos na datas especificada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum evento encontrado para essa data")
    })
    public ResponseEntity<?> buscarEventosPorData(@Parameter(description = "Data a ser buscada") @RequestParam LocalDate data) {
        List<EventoDTO> eventos = eventoService.findEventosByDateRange(data);
        if (!eventos.isEmpty()) {
            return ResponseEntity.ok(eventos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum evento encontrado para essa data");
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

    @PutMapping("/add-interesse")
    @Operation(summary = "Adiciona as tags ao interesse de um usuário", description = "Adiciona as tags ao interesse de um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Interesse atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento ou usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    public ResponseEntity<?> adicionarInteresseUsuario(@RequestBody InteresseRequestDTO interesseRequestDTO) {

        try {
            Integer idEvento = interesseRequestDTO.getIdEvento();
            Integer idUsuario = interesseRequestDTO.getIdUsuario();
            List<String> tags = interesseRequestDTO.getTags() != null ? interesseRequestDTO.getTags() : Collections.emptyList();

            usuarioTagService.saveUsuarioTag(idUsuario, idEvento, tags);

            return ResponseEntity.ok("Interesse do usuário atualizado com sucesso.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento ou usuário não encontrado.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar interesse do usuário: " + e.getMessage());
        }
    }

    @GetMapping("/find-interesse")
    @Operation(summary = "Busca o interesse no evento para o usuário", description = "Retorna a quantidade de interesses no evento e se o usuário já demonstrou interesse.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados de interesse retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
    })
    public ResponseEntity<?> getQtInteresseAndUserInterest(
            @RequestParam Long idEvento,
            @RequestParam Long idUsuario) {

        Map<String, Object> interesseData = eventoService.getQtInteresseAndUserInterest(idEvento, idUsuario);
        if (interesseData.containsKey("qtInteresse")) {
            return ResponseEntity.ok(interesseData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento não encontrado.");
        }
    }
}
