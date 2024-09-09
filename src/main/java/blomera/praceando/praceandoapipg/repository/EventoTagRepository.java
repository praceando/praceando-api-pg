/*
 * Class: EventoTagRepository
 * Description: Repository for the EventoTag entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 06/09/2024
 * Last Updated: 06/09/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.EventoTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoTagRepository extends JpaRepository<EventoTag, Long> {
    List<EventoTag> findEventoTagByTag_IdOrderById();
}
