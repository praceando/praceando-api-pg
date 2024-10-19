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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Types;
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
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("PRC_INSERIR_EVENTO_TAGS");

        try {
            java.sql.Array tagsArray = jdbcTemplate.getDataSource().getConnection().createArrayOf("VARCHAR", tags.toArray());

            MapSqlParameterSource params = new MapSqlParameterSource()
                    .addValue("p_nm_evento", evento.getNmEvento(), Types.VARCHAR)
                    .addValue("p_ds_evento", evento.getDsEvento(), Types.VARCHAR)
                    .addValue("p_dt_inicio", java.sql.Date.valueOf(evento.getDtInicio()), Types.DATE)
                    .addValue("p_hr_inicio", java.sql.Time.valueOf(evento.getHrInicio()), Types.TIME)
                    .addValue("p_dt_fim", java.sql.Date.valueOf(evento.getDtFim()), Types.DATE)
                    .addValue("p_hr_fim", java.sql.Time.valueOf(evento.getHrFim()), Types.TIME)
                    .addValue("p_url_documentacao", evento.getUrlDocumentacao(), Types.VARCHAR)
                    .addValue("p_cd_local", evento.getLocal().getId(), Types.INTEGER)
                    .addValue("p_cd_anunciante", evento.getAnunciante().getId(), Types.INTEGER)
                    .addValue("p_tags", tagsArray, Types.ARRAY);

            jdbcCall.execute(params);

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
