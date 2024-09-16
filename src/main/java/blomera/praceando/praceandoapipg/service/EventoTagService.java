/*
 * Class: EventoTagService
 * Description: Service for the EventoTag entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 16/09/2024
 * Last Updated: 16/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.EventoTag;
import blomera.praceando.praceandoapipg.repository.EventoTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoTagService {

    private final EventoTagRepository eventoTagRepository;

    public EventoTagService(EventoTagRepository eventoTagRepository) {
        this.eventoTagRepository = eventoTagRepository;
    }

    /**
     * @return uma lista de objetos EventoTag se existirem, ou null se não houver nenhum EventoTag.
     */
    public List<EventoTag> getEventoTags() {
        List<EventoTag> eventoTags = eventoTagRepository.findAll();
        return eventoTags.isEmpty() ? null : eventoTags;
    }

    /**
     * @return EventoTag pelo id, se ele existir, caso contrário, retorna null
     */
    public EventoTag getEventoTagById(Long id) {
        Optional<EventoTag> eventoTag = eventoTagRepository.findById(id);
        return eventoTag.orElse(null);
    }

    /**
     * @return lista de EventoTag por id da tag, ordenada por id.
     */
    public List<EventoTag> getEventoTagsByTagId(Long tagId) {
        return eventoTagRepository.findEventoTagsByTag_IdOrderById();
    }

    /**
     * @return EventoTag deletado.
     */
    public EventoTag deleteEventoTagById(Long id) {
        EventoTag eventoTag = getEventoTagById(id);
        if (eventoTag != null) eventoTagRepository.deleteById(id);
        return eventoTag;
    }

    /**
     * @return EventoTag inserido.
     */
    public EventoTag saveEventoTag(EventoTag eventoTag) {
        return eventoTagRepository.save(eventoTag);
    }

    /**
     * Atualiza os dados de um EventoTag.
     * @param id Id do EventoTag a ser atualizado.
     * @param eventoTag EventoTag com os novos dados.
     * @return EventoTag atualizado ou nulo ou nulo, caso a Tag do Evento não exista
     */
    public EventoTag updateEventoTag(Long id, EventoTag eventoTag) {
        EventoTag existingEventoTag = getEventoTagById(id);
        if (existingEventoTag != null) {
            existingEventoTag.setDtAtualizacao(eventoTag.getDtAtualizacao());
            existingEventoTag.setTag(eventoTag.getTag());
            existingEventoTag.setEvento(eventoTag.getEvento());
            return eventoTagRepository.save(existingEventoTag);
        }
        return null;
    }
}
