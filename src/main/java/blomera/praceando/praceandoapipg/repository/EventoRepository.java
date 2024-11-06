/*
 * Class: EventoRepository
 * Description: Repository for the Evento entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 06/09/2024
 * Last Updated: 21/10/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Evento;
import blomera.praceando.praceandoapipg.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query(value = "SELECT e.id_evento, e.nm_evento, l.nm_local, e.dt_inicio, e.hr_inicio, e.dt_fim, e.hr_fim, array_agg(t.nm_tag) AS tags " +
            "FROM evento e " +
            "JOIN local l ON l.id_local = e.cd_local " +
            "JOIN evento_tag et ON et.cd_evento = e.id_evento " +
            "JOIN tag t ON t.id_tag = et.cd_tag " +
            "WHERE e.cd_anunciante = :idAnunciante " +
            "AND e.dt_desativacao IS NULL " +
            "GROUP BY e.id_evento, e.nm_evento, l.nm_local, e.dt_inicio, e.hr_inicio, e.dt_fim, e.hr_fim ", nativeQuery = true)
    List<Object[]> findEventosByAnuncianteWithTags(@Param("idAnunciante") Long idAnunciante);

    @Query(value = "SELECT e.id_evento, e.nm_evento, l.nm_local, e.dt_inicio, e.hr_inicio, e.dt_fim, e.hr_fim, array_agg(t.nm_tag) AS tags " +
            "FROM evento e " +
            "JOIN local l ON l.id_local = e.cd_local " +
            "JOIN evento_tag et ON et.cd_evento = e.id_evento " +
            "JOIN tag t ON t.id_tag = et.cd_tag " +
            "WHERE :data BETWEEN e.dt_inicio AND e.dt_fim " +
            "AND e.dt_desativacao IS NULL " +
            "GROUP BY e.id_evento, e.nm_evento, l.nm_local, e.dt_inicio, e.hr_inicio, e.dt_fim, e.hr_fim " +
            "ORDER BY e.id_evento", nativeQuery = true)
    List<Object[]> findEventosByDate(@Param("data") LocalDate data);

    @Override
    @Query(value = "SELECT * FROM evento e WHERE e.dt_desativacao IS NULL", nativeQuery = true)
    List<Evento> findAll();

    @Query(value = "SELECT e.id_evento, e.nm_evento, l.nm_local, e.dt_inicio, e.hr_inicio, e.dt_fim, e.hr_fim, array_agg(t.nm_tag) AS tags " +
            "FROM evento e " +
            "JOIN local l ON l.id_local = e.cd_local " +
            "JOIN evento_tag et ON et.cd_evento = e.id_evento " +
            "JOIN tag t ON t.id_tag = et.cd_tag " +
            "WHERE e.id_evento IN :ids " +
            "AND e.dt_desativacao IS NULL " +
            "GROUP BY e.id_evento, e.nm_evento, l.nm_local, e.dt_inicio, e.hr_inicio, e.dt_fim, e.hr_fim", nativeQuery = true)
    List<Object[]> findAllWithTagsByIds(@Param("ids") List<Long> ids);

    @Query(value = "SELECT e.id_evento, e.nm_evento, l.nm_local, e.dt_inicio, e.hr_inicio, e.dt_fim, e.hr_fim, array_agg(t.nm_tag) AS tags " +
            "FROM evento e " +
            "JOIN local l ON l.id_local = e.cd_local " +
            "JOIN evento_tag et ON et.cd_evento = e.id_evento " +
            "JOIN tag t ON t.id_tag = et.cd_tag " +
            "WHERE e.dt_desativacao IS NULL " +
            "GROUP BY e.id_evento, e.nm_evento, l.nm_local, e.dt_inicio, e.hr_inicio, e.dt_fim, e.hr_fim", nativeQuery = true)
    List<Object[]> findAllWithTags();

    @Query(value = "SELECT e.id_evento, e.nm_evento, l.nm_local, e.dt_inicio, e.hr_inicio, e.dt_fim, e.hr_fim, array_agg(t.nm_tag) AS tags " +
            "FROM evento e " +
            "JOIN local l ON l.id_local = e.cd_local " +
            "JOIN evento_tag et ON et.cd_evento = e.id_evento " +
            "JOIN tag t ON t.id_tag = et.cd_tag " +
            "WHERE e.id_evento IN (SELECT et2.cd_evento FROM evento_tag et2 WHERE et2.cd_tag = :idTag) " +
            "AND e.dt_desativacao IS NULL " +
            "GROUP BY e.id_evento, e.nm_evento, l.nm_local, e.dt_inicio, e.hr_inicio, e.dt_fim, e.hr_fim ", nativeQuery = true)
    List<Object[]> findEventosByTagWithTags(@Param("idTag") Long idTag);

    @Override
    @Query(value = "SELECT * FROM evento e WHERE e.dt_desativacao IS NULL AND e.id_evento = :id", nativeQuery = true)
    Optional<Evento> findById(@Param("id") Long id);
}
