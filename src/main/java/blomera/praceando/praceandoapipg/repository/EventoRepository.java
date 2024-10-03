/*
 * Class: EventoRepository
 * Description: Repository for the Evento entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 06/09/2024
 * Last Updated: 23/09/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findEventosByAnunciante_IdOrderById(Long anuncianteId);
    List<Evento> findEventosByDtFimAndDtInicioOrderById(LocalDateTime dtFim, LocalDateTime dtInicio);
}
