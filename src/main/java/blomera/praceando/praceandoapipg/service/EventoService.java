/*
 * Class: EventoService
 * Description: Service for the Evento entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 16/09/2024
 * Last Updated: 23/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.dto.EventoDTO;
import blomera.praceando.praceandoapipg.model.Evento;
import blomera.praceando.praceandoapipg.repository.EventoRepository;
import blomera.praceando.praceandoapipg.repository.InteresseRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final InteresseRepository interesseRepository;
    private final JdbcTemplate jdbcTemplate;

    public EventoService(EventoRepository eventoRepository, JdbcTemplate jdbcTemplate, InteresseRepository interesseRepository) {
        this.eventoRepository = eventoRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.interesseRepository = interesseRepository;
    }

    /**
     * @param ids Lista de IDs de evento a serem buscados
     * @return uma lista de objetos EventoDTO (incluindo tags) se existirem, ou null se não houver eventos para os IDs fornecidos.
     */
    @Cacheable(value = "eventos", cacheManager = "cacheManager")
    public List<EventoDTO> getEventosPorIds(List<Long> ids) {
        List<Object[]> resultados = eventoRepository.findAllWithTagsByIds(ids);

        if (resultados == null || resultados.isEmpty()) {
            return null;
        }

        List<EventoDTO> eventos = new ArrayList<>();
        for (Object[] resultado : resultados) {
            Long idEvento = (Long) resultado[0];
            String nomeEvento = (String) resultado[1];
            String nomeLocal = (String) resultado[2];
            LocalDate dataInicio = ((java.sql.Date) resultado[3]).toLocalDate();
            LocalTime horaInicio = ((java.sql.Time) resultado[4]).toLocalTime();
            LocalDate dataFim = ((java.sql.Date) resultado[5]).toLocalDate();
            LocalTime horaFim = ((java.sql.Time) resultado[6]).toLocalTime();
            String[] tagsArray = (String[]) resultado[7];
            List<String> tags = Arrays.asList(tagsArray);

            EventoDTO eventoDTO = new EventoDTO(idEvento, nomeEvento, nomeLocal, dataInicio, horaInicio, dataFim, horaFim, tags);
            eventos.add(eventoDTO);
        }
        return eventos;
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
    public List<EventoDTO> findEventosByAnunciante(Long idAnunciante) {
        List<Object[]> resultados = eventoRepository.findEventosByAnuncianteWithTags(idAnunciante);

        if (resultados == null) {
            return null;
        }

        List<EventoDTO> eventos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Long idEvento = (Long) resultado[0];
            String nomeEvento = (String) resultado[1];
            String nomeLocal = (String) resultado[2];
            LocalDate dataInicio = ((java.sql.Date) resultado[3]).toLocalDate();
            LocalTime horaInicio = ((java.sql.Time) resultado[4]).toLocalTime();
            LocalDate dataFim = ((java.sql.Date) resultado[5]).toLocalDate();
            LocalTime horaFim = ((java.sql.Time) resultado[6]).toLocalTime();

            String[] tagsArray = (String[]) resultado[7];
            List<String> tags = Arrays.asList(tagsArray);

            EventoDTO eventoDTO = new EventoDTO(idEvento, nomeEvento, nomeLocal, dataInicio, horaInicio, dataFim, horaFim, tags);
            eventos.add(eventoDTO);
        }

        return eventos;
    }

    /**
     * @param data data na qual os eventos serão buscados
     * @return lista de eventos ativos na data.
     */
    public List<EventoDTO> findEventosByDateRange(LocalDate data) {
        List<Object[]> resultados = eventoRepository.findEventosByDate(data);

        if (resultados == null) {
            return null;
        }

        List<EventoDTO> eventos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Long idEvento = (Long) resultado[0];
            String nomeEvento = (String) resultado[1];
            String nomeLocal = (String) resultado[2];
            LocalDate dataInicio = ((java.sql.Date) resultado[3]).toLocalDate();
            LocalTime horaInicio = ((java.sql.Time) resultado[4]).toLocalTime();
            LocalDate dataFim = ((java.sql.Date) resultado[5]).toLocalDate();
            LocalTime horaFim = ((java.sql.Time) resultado[6]).toLocalTime();
            String[] tagsArray = (String[]) resultado[7];
            List<String> tags = Arrays.asList(tagsArray);

            EventoDTO eventoDTO = new EventoDTO(idEvento, nomeEvento, nomeLocal, dataInicio, horaInicio, dataFim, horaFim, tags);
            eventos.add(eventoDTO);
        }

        return eventos;
    }

    /**
     * @return lista de eventos por tag.
     */
    public List<EventoDTO> findEventosByTag(Long idTag) {
        List<Object[]> resultados = eventoRepository.findEventosByTagWithTags(idTag);

        if (resultados == null) {
            return null;
        }

        List<EventoDTO> eventos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Long idEvento = (Long) resultado[0];
            String nomeEvento = (String) resultado[1];
            String nomeLocal = (String) resultado[2];
            LocalDate dataInicio = ((java.sql.Date) resultado[3]).toLocalDate();
            LocalTime horaInicio = ((java.sql.Time) resultado[4]).toLocalTime();
            LocalDate dataFim = ((java.sql.Date) resultado[5]).toLocalDate();
            LocalTime horaFim = ((java.sql.Time) resultado[6]).toLocalTime();
            String[] tagsArray = (String[]) resultado[7];
            List<String> tags = Arrays.asList(tagsArray);

            EventoDTO eventoDTO = new EventoDTO(idEvento, nomeEvento, nomeLocal, dataInicio, horaInicio, dataFim, horaFim, tags);
            eventos.add(eventoDTO);
        }

        return eventos;
    }

    public Map<String, Object> getQtInteresseAndUserInterest(Long idEvento, Long idUsuario) {
        Map<String, Object> response = new HashMap<>();

        Optional<Evento> evento = eventoRepository.findById(idEvento);
        if (evento.isPresent()) {
            Evento eventoData = evento.get();
            response.put("qtInteresse", eventoData.getQtInteresse());

            boolean userHasInterest = interesseRepository.existsByConsumidorIdAndEventoId(idUsuario, idEvento);
            response.put("userInteressou", userHasInterest);
        } else {
            response.put("message", "Evento não encontrado.");
        }

        return response;
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
    public Integer saveEvento(Evento evento, List<String> tags) {
        try {
            evento.setQtInteresse(0);

            java.sql.Array tagsArray = jdbcTemplate.getDataSource().getConnection().createArrayOf("VARCHAR", tags.toArray());

            return jdbcTemplate.execute((ConnectionCallback<Integer>) con -> {
                try (CallableStatement callableStatement = con.prepareCall("CALL PRC_INSERIR_EVENTO_TAG(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
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

                    callableStatement.registerOutParameter(11, java.sql.Types.INTEGER);

                    callableStatement.execute();

                    return callableStatement.getInt(11);
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
