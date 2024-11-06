/*
 * Class: InteresseRepository
 * Description: Repository for the Interesse entity
 * Author: Camilla Ucci de Menezes
 * Creation Date: 09/09/2024
 * Last Updated: 09/09/2024
 */
package blomera.praceando.praceandoapipg.repository;

import blomera.praceando.praceandoapipg.model.Interesse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteresseRepository extends JpaRepository<Interesse, Long> {
    boolean existsByConsumidorIdAndEventoId(Long idUsuario, Long idEvento);
}

