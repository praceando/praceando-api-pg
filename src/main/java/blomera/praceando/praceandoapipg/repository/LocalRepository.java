/*
 * Class: LocalRepository
 * Description: Repository for the Local entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 09/09/2024
 * Last Updated: 09/09/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocalRepository extends JpaRepository<Local, Long> {
    @Override
    @Query(value = "SELECT * FROM local l WHERE l.dt_desativacao IS NULL", nativeQuery = true)
    List<Local> findAll();

    @Override
    @Query(value = "SELECT * FROM local l WHERE l.dt_desativacao IS NULL AND p.id_local = :id", nativeQuery = true)
    Optional<Local> findById(@Param("id") Long id);
}
