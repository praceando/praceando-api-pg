/*
 * Class: EventoService
 * Description: Service for the Evento entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 16/09/2024
 * Last Updated: 23/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Evento;
import blomera.praceando.praceandoapipg.model.EventoTag;
import blomera.praceando.praceandoapipg.repository.EventoRepository;
import blomera.praceando.praceandoapipg.repository.EventoTagRepository;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EventoTagRepository eventoTagRepository;
    private final JdbcTemplate jdbcTemplate;

    public EventoService(EventoRepository eventoRepository, EventoTagRepository eventoTagRepository, JdbcTemplate jdbcTemplate) {
        this.eventoRepository = eventoRepository;
        this.eventoTagRepository = eventoTagRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return uma lista de objetos Evento se existirem, ou null se não houver nenhum evento.
     */
    public List<Evento> getEventos() {
        List<Evento> eventos = eventoRepository.findAll();
        return eventos.isEmpty() ? null : eventos;
    }

    /**
     * @return evento pelo id, se ele existir, caso contrário, retorna null
     */
    public Evento getEventoById(Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        return evento.orElse(null);
    }

    /**
     * @return lista de eventos por anunciante.
     */
    public List<Evento> findEventosByAnunciante(Long anuncianteId) {
        return eventoRepository.findEventosByAnunciante_IdOrderById(anuncianteId);
    }

    /**
     * @param dtFim Id do evento a ser atualizado.
     * @param dtInicio Evento com os novos dados.
     * @return lista de eventos com base na data de início e fim.
     */
    public List<Evento> findEventosByDateRange(LocalDateTime dtInicio, LocalDateTime dtFim) {
        return eventoRepository.findEventosByDtFimAndDtInicioOrderById(dtFim, dtInicio);
    }

    /**
     * @return lista de eventos por tag.
     */
    public List<Evento> findEventosByTag(Long tagId) {
        List<EventoTag> eventoTags = eventoTagRepository.findEventoTagsByTag_IdOrderById(tagId);
        return eventoTags.stream()
                .map(EventoTag::getEvento)
                .collect(Collectors.toList());
    }

    /**
     * @return evento deletado.
     */
    public Evento deleteEventoById(Long id) {
        Evento evento = getEventoById(id);
        if (evento != null) eventoRepository.deleteById(id);
        return evento;
    }

    /**
     * Método para inserir um evento junto com suas tags.
     */
    public void saveEvento(Evento evento, List<String> tags) {
        try {
            java.sql.Array tagsArray = jdbcTemplate.getDataSource().getConnection().createArrayOf("VARCHAR", tags.toArray());

            jdbcTemplate.execute((ConnectionCallback<Void>) con -> {
                try (CallableStatement callableStatement = con.prepareCall("CALL PRC_INSERIR_EVENTO_TAGS(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                    callableStatement.setString(1, evento.getNmEvento());
                    callableStatement.setString(2, evento.getDsEvento());
                    callableStatement.setDate(3, java.sql.Date.valueOf(evento.getDtInicio()));
                    callableStatement.setTime(4, java.sql.Time.valueOf(evento.getHrInicio()));
                    callableStatement.setDate(5, java.sql.Date.valueOf(evento.getDtFim()));
                    callableStatement.setTime(6, java.sql.Time.valueOf(evento.getHrFim()));
                    callableStatement.setString(7, evento.getUrlDocumentacao());
                    callableStatement.setInt(8, Long.valueOf(evento.getLocal().getId()).intValue());
                    callableStatement.setInt(9, evento.getAnunciante().getId().intValue());
                    callableStatement.setArray(10, tagsArray);
                    callableStatement.execute();
                    return null;
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar o evento e tags: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de um evento.
     * @param id Id do evento a ser atualizado.
     * @param evento Evento com os novos dados.
     * @return evento atualizado ou nulo, caso o evento não exista
     */
    public Evento updateEvento(Long id, Evento evento) {
        Evento existingEvento = getEventoById(id);
        if (existingEvento != null) {
            existingEvento.setLocal(evento.getLocal());
            existingEvento.setAnunciante(evento.getAnunciante());
            existingEvento.setQtInteresse(evento.getQtInteresse());
            existingEvento.setNmEvento(evento.getNmEvento());
            existingEvento.setDsEvento(evento.getDsEvento());
            existingEvento.setDtInicio(evento.getDtInicio());
            existingEvento.setHrInicio(evento.getHrInicio());
            existingEvento.setDtFim(evento.getDtFim());
            existingEvento.setHrFim(evento.getHrFim());
            existingEvento.setUrlDocumentacao(evento.getUrlDocumentacao());
            existingEvento.setDtAtualizacao(LocalDateTime.now());
            return eventoRepository.save(existingEvento);
        }
        return null;
    }

    /**
     * @return evento excluido.
     */
    public Optional<Evento> softDelete(Long id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        if (evento.isPresent()) {
            Evento e = evento.get();
            e.setDtDesativacao(LocalDateTime.now());
            eventoRepository.save(e);
        }
        return evento;
    }
}
