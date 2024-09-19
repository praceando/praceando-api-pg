/*
 * Class: EventoService
 * Description: Service for the Evento entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 16/09/2024
 * Last Updated: 16/09/2024
 */

package blomera.praceando.praceandoapipg.service;

import blomera.praceando.praceandoapipg.model.Evento;
import blomera.praceando.praceandoapipg.repository.EventoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
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
    public List<Evento> getEventosByAnuncianteId(Long anuncianteId) {
        return eventoRepository.findEventosByAnunciante_IdOrderById(anuncianteId);
    }

    /**
     * @param dtFim Id do evento a ser atualizado.
     * @param dtInicio Evento com os novos dados.
     * @return lista de eventos com base na data de início e fim.
     */
    public List<Evento> getEventosByDataInicioAndFim(LocalDateTime dtFim, LocalDateTime dtInicio) {
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
}
