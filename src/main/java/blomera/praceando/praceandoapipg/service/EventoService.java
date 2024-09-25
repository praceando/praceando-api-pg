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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EventoTagRepository eventoTagRepository;

    public EventoService(EventoRepository eventoRepository, EventoTagRepository eventoTagRepository) {
        this.eventoRepository = eventoRepository;
        this.eventoTagRepository = eventoTagRepository;
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
     * @return evento deletado.
     */
    public Evento deleteEventoById(Long id) {
        Evento evento = getEventoById(id);
        if (evento != null) eventoRepository.deleteById(id);
        return evento;
    }

    /**
     * @return evento inserido.
     */
    public Evento saveEvento(Evento evento) {
        return eventoRepository.save(evento);
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
            existingEvento.setQtInteresse(evento.getQtInteresse());
            existingEvento.setNmEvento(evento.getNmEvento());
            existingEvento.setDsEvento(evento.getDsEvento());
            existingEvento.setDtInicio(evento.getDtInicio());
            existingEvento.setDtFim(evento.getDtFim());
            existingEvento.setUrlDocumentacao(evento.getUrlDocumentacao());
            existingEvento.setDtAtualizacao(evento.getDtAtualizacao());
            existingEvento.setLocal(evento.getLocal());
            existingEvento.setAnunciante(evento.getAnunciante());
            return eventoRepository.save(existingEvento);
        }
        return null;
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
}
