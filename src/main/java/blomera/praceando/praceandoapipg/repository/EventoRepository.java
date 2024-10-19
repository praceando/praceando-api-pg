/*
 * Class: EventoRepository
 * Description: Repository for the Evento entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 06/09/2024
 * Last Updated: 23/09/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Evento;
import blomera.praceando.praceandoapipg.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    List<Evento> findEventosByAnunciante_IdOrderById(Long anuncianteId);
    List<Evento> findEventosByDtFimAndDtInicioOrderById(LocalDateTime dtFim, LocalDateTime dtInicio);
    @Override
    @Query(value = "SELECT * FROM evento e WHERE e.dt_desativacao IS NULL", nativeQuery = true)
    List<Evento> findAll();

    @Override
    @Query(value = "SELECT * FROM evento e WHERE e.dt_desativacao IS NULL AND e.id_evento = :id", nativeQuery = true)
    Optional<Evento> findById(@Param("id") Long id);
}
